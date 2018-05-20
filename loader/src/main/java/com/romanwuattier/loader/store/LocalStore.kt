package com.romanwuattier.loader.store

import com.romanwuattier.loader.FileReaderTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.SaveTask
import com.romanwuattier.loader.data.LocalRequest
import com.romanwuattier.loader.data.Request
import com.romanwuattier.loader.filereader.FileReaderModule
import java.util.concurrent.*

internal class LocalStore<K, V> : Store.Local<K, V> {

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = FileReaderModule.provideInstance()

    override fun fetch(loadRequest: Request, callback: LoaderCallback) {
        val localRequest: LocalRequest = loadRequest as LocalRequest
        val converter = module.getConverterStrategy(localRequest.converterType)
        val task = FileReaderTask<K, V>(localRequest, converter)
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