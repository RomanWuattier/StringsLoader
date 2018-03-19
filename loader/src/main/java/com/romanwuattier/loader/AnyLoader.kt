package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.data.LoadRequest
import com.romanwuattier.loader.store.MemoryStore
import com.romanwuattier.loader.utils.checkMainThread

class AnyLoader : Loader {

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

    override fun <K, V> load(url: String, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        val request = LoadRequest(url, converterType, callback)
        val store = module.getStore()
        store.fetch<K, V>(request)
    }

    override fun <K, V> get(key: K): V {
        checkMainThread()

        val store = module.getStore()
        val value = if (store is MemoryStore) {
            store.get<K, V>(key)
        } else {
            throw IllegalStateException("The memory store has not been initialized yet. Make sure to load data before.")
        }
        return value ?: throw IllegalArgumentException("Translation key doesn't exist")
    }
}