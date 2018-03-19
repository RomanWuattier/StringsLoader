package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.DownloadTask
import com.romanwuattier.stringsloader.LoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
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

    override fun <K, V> fetch(request: LoadRequest) {
        val task = DownloadTask<K, V>(request, ::onSuccess, ::onError)
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