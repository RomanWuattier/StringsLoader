package com.romanwuattier.loader

import android.content.Context
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.data.LocalRequest
import com.romanwuattier.loader.data.RemoteRequest
import com.romanwuattier.loader.data.Request
import com.romanwuattier.loader.store.Store
import com.romanwuattier.loader.utils.checkMainThread
import java.io.File

class AnyLoader private constructor() : Loader {

    companion object Provider {
        @Volatile
        private var instance: AnyLoader? = null

        @Synchronized
        fun provideInstance(): AnyLoader {
            if (instance == null) {
                instance = AnyLoader()
            }
            return instance as AnyLoader
        }
    }

    private val module = LoaderModule.provideInstance()

    @Synchronized
    override fun <K, V> loadFromRemote(url: String, cacheDir: File, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        val remoteRequest = RemoteRequest(url, cacheDir, converterType)
        val store = module.getRemoteStore<K, V>()
        load(remoteRequest, store, callback)
    }

    @Synchronized
    override fun <K, V> loadFromLocal(path: String, context: Context, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        val localRequest = LocalRequest(context, path, converterType)
        val store = module.getLocalStore<K, V>()
        load(localRequest, store, callback)
    }

    private fun <K, V> load(request: Request, store: Store.AsyncStore<K, V>, callback: LoaderCallback) {
        store.fetch(request, callback)
    }

    override fun <K, V> get(key: K): V? {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.get(key)
    }

    @Synchronized
    override fun <K, V> clear(): Boolean {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.clear()
    }
}
