package com.romanwuattier.loader.store

import com.romanwuattier.loader.DownloadTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.LoaderModule
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class RemoteStore<K, V> private constructor() : Store.Remote<K, V> {

    internal companion object Provider: Store.GenericProvider {
        @Volatile
        private var instance: Store.Remote<*, *>? = null

        @Suppress("UNCHECKED_CAST")
        @Synchronized
        override fun <K, V> provideInstance(): Store.Remote<K, V> {
            if (instance == null) {
                instance = RemoteStore<K, V>()
            }
            return instance as Store.Remote<K, V>
        }
    }

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = LoaderModule.provideInstance()

    override fun fetch(request: LoadRequest) {
        val okHttpClient = module.getOkHttpClient()
        val converter = module.getConverterStrategy(request.converterType)
        val task = DownloadTask<K, V>(request, okHttpClient, converter)
        val future = worker.submit(task)

        try {
            onSuccess(future.get(), request.callback)
        } catch (e: ExecutionException) {
            onError(e, request.callback)
        }
    }

    override fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        updateMemoryStore(map)
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }

     private fun updateMemoryStore(map: ConcurrentHashMap<K, V>) {
        val memoryStore = StoreModule.provideInstance().getMemoryStore<K, V>()
         memoryStore.putAll(map)
    }
}