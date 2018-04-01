package com.romanwuattier.loader.store

import com.romanwuattier.loader.DownloadTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.SaveTask
import com.romanwuattier.loader.data.LoadRequest
import com.romanwuattier.loader.downloader.DownloaderModule
import java.util.concurrent.*

internal class RemoteStore<K, V> : Store.Remote<K, V> {

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = DownloaderModule.provideInstance()

    override fun fetch(loadRequest: LoadRequest) {
        val okHttpClient = module.getOkHttpClient(loadRequest.cacheDir)
        val request = module.getRequest(loadRequest.url)
        val converter = module.getConverterStrategy(loadRequest.converterType)
        val task = DownloadTask<K, V>(okHttpClient, request, converter)
        val future = worker.submit(task)

        try {
            onSuccess(future.get(), loadRequest.callback)
        } catch (e: ExecutionException) {
            onError(e, loadRequest.callback)
        } catch (e: CancellationException) {
            onError(e, loadRequest.callback)
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