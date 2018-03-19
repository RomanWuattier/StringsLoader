package com.romanwuattier.loader.store

import com.romanwuattier.loader.DownloadTask
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.LoaderModule
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class RemoteStore : Store, Store.Remote {

    internal companion object Provider {
        @Volatile
        private var instance: RemoteStore? = null

        @Synchronized
        fun provideInstance(): RemoteStore {
            if (instance == null) {
                instance = RemoteStore()
            }
            return instance as RemoteStore
        }
    }

    private val worker: ExecutorService = Executors.newSingleThreadExecutor()

    private val module = LoaderModule.provideInstance()

    override fun <K, V> fetch(request: LoadRequest) {
        val okHttpClient = module.getOkHttpClient()
        val converter = module.getConverterStrategy(request.converterType)
        val task = DownloadTask<K, V>(request, okHttpClient, converter, ::onSuccess, ::onError)
        worker.submit(task)
    }

    override fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        updateMemoryStore(map)
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }

    private fun <K, V> updateMemoryStore(map: ConcurrentHashMap<K, V>) {
        MemoryStore.provideInstance().memoryMap = map
    }
}