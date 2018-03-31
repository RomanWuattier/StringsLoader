package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.data.LoadRequest
import com.romanwuattier.loader.store.MemoryStore
import com.romanwuattier.loader.store.Store
import com.romanwuattier.loader.utils.checkMainThread

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

    override fun <K, V> load(url: String, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        request = LoadRequest(url, converterType, callback)
        val store = module.getRemoteStore<K, V>()
        load(request, store)
    }

    override fun <K, V> reload(callback: LoaderCallback) {
        checkMainThread()

        if (!::request.isInitialized) {
            throw IllegalStateException()
        }

        val store = module.getStorePolicy<K, V>()
        load(request, store)
    }

    private fun <K, V> load(request: LoadRequest, store: Store<K, V>) {
        store.fetch(request)
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

    override fun <K, V> clear(): Boolean {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.clear()
    }
}