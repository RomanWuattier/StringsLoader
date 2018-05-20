package com.romanwuattier.loader

import android.content.Context
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.data.RemoteRequest
import com.romanwuattier.loader.data.Request
import com.romanwuattier.loader.store.MemoryStore
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

    private lateinit var remoteRequest: Request

    private lateinit var locaRequest: Request

    @Synchronized
    override fun <K, V> loadFromRemote(url: String, cacheDir: File, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        remoteRequest = RemoteRequest(url, cacheDir, converterType)
        val store = module.getRemoteStore<K, V>()
        load(remoteRequest, store, callback)
    }

    @Synchronized
    override fun <K, V> loadFromLocal(context: Context, converterType: ConverterType, callback: LoaderCallback) {
    }

    @Synchronized
    override fun <K, V> reloadFromRemote(callback: LoaderCallback) {
        checkMainThread()

        if (!::remoteRequest.isInitialized) {
            throw IllegalStateException()
        }

        val store = module.getRemoteStore<K, V>()
        load(remoteRequest, store, callback)
    }

    @Synchronized
    override fun <K, V> reloadFromLocal(callback: LoaderCallback) {
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
