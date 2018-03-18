package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.DownloadTask
import com.romanwuattier.stringsloader.StringsLoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RemoteStore: Store.Remote {

    companion object Provider {
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

    override fun <K, V> fetch(request: LoadRequest,
                       onSuccess: (ConcurrentHashMap<K, V>, StringsLoaderCallback) -> Unit,
                       onError: (Throwable, StringsLoaderCallback) -> Unit) {
        val task = DownloadTask(request, onSuccess, onError)
        worker.submit(task)
    }
}