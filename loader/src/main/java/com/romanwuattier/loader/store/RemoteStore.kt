package com.romanwuattier.loader.store

import com.romanwuattier.loader.task.DownloadTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.task.SaveTask
import com.romanwuattier.loader.data.RemoteRequest
import com.romanwuattier.loader.data.Request
import com.romanwuattier.loader.downloader.DownloaderModule
import java.util.concurrent.*

internal class RemoteStore<K, V> : Store.Remote<K, V> {

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = DownloaderModule.provideInstance()

    override fun fetch(loadRequest: Request, callback: LoaderCallback) {
        val remoteRequest: RemoteRequest = loadRequest as RemoteRequest
        val okHttpClient = module.getOkHttpClient(remoteRequest.cacheDir)
        val request = module.getRequest(remoteRequest.url)
        val converter = module.getConverterStrategy(remoteRequest.converterType)
        val task = DownloadTask<K, V>(okHttpClient, request, converter)
        val future = worker.submit(task)

        try {
            onSuccess(future.get(), callback)
        } catch (e: ExecutionException) {
            onError(e, callback)
        } catch (e: CancellationException) {
            onError(e, callback)
        }
    }

    override fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        updateMemoryStore(map, callback)
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError(throwable)
    }

    private fun updateMemoryStore(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        val memoryStore = StoreModule.provideInstance<K, V>().getMemoryStore()
        val task = SaveTask(map, memoryStore)
        val future = worker.submit(task)

        try {
            if (future.get()) {
                callback.onComplete()
            }
        } catch (e: ExecutionException) {
            callback.onError(e)
        }
    }
}
