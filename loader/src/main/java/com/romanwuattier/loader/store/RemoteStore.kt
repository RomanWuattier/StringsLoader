package com.romanwuattier.loader.store

import com.romanwuattier.loader.DownloadTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.LoaderModule
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class RemoteStore : Store.Remote {

    internal companion object Provider {
        @Volatile
        private var instance: Store.Remote? = null

        @Synchronized
        fun provideInstance(): Store.Remote {
            if (instance == null) {
                instance = RemoteStore()
            }
            return instance as Store.Remote
        }
    }

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = LoaderModule.provideInstance()

    override fun <K, V> fetch(request: LoadRequest) {
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

    override fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        updateMemoryStore(map)
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }

     private fun <K, V> updateMemoryStore(map: ConcurrentHashMap<K, V>) {
        val memoryStore = StoreModule.provideInstance().getMemoryStore()
         memoryStore.putAll(map)
    }
}