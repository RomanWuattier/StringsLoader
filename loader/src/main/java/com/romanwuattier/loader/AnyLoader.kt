package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.data.LoadRequest
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

    private lateinit var request: LoadRequest

    @Synchronized
    override fun <K, V> load(url: String, cacheDir: File, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        request = LoadRequest(url, cacheDir, converterType)
        val store = module.getStorePolicy<K, V>()
        load(request, store, callback)
    }

    @Synchronized
    override fun <K, V> reload(callback: LoaderCallback) {
        checkMainThread()

        if (!::request.isInitialized) {
            throw IllegalStateException()
        }

        val store = module.getRemoteStore<K, V>()
        load(request, store, callback)
    }

    private fun <K, V> load(request: LoadRequest, store: Store<K, V>, callback: LoaderCallback) {
        store.fetch(request, callback)
    }

    override fun <K, V> get(key: K): V? {
        checkMainThread()

        val store = module.getStorePolicy<K, V>()
        return if (store is MemoryStore<K, V>) {
            store.get(key)
        } else {
            null
        }
    }

    @Synchronized
    override fun <K, V> clear(): Boolean {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.clear()
    }
}
