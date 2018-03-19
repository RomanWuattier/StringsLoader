package com.romanwuattier.stringsloader

import com.romanwuattier.stringsloader.converter.ConverterType
import com.romanwuattier.stringsloader.data.LoadRequest
import com.romanwuattier.stringsloader.store.MemoryStore
import com.romanwuattier.stringsloader.utils.checkMainThread

class StringsLoader private constructor() : Loader {

    companion object Provider {
        @Volatile
        private var instance: StringsLoader? = null

        @Synchronized
        fun provideTranslationLoader(): StringsLoader {
            checkMainThread()

            if (instance == null) {
                instance = StringsLoader()
            }
            return instance as StringsLoader
        }
    }

    @Synchronized
    override fun load(url: String, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        val request = LoadRequest(url, converterType, callback)
        val store = StringsLoaderModule.provideInstance().getStore()
        store.fetch<Any, String>(request)
    }

    override fun get(key: Any): String {
        checkMainThread()

        val store = StringsLoaderModule.provideInstance().getStore()
        val value = if (store is MemoryStore) {
            store.get<Any, String>(key)
        } else {
            throw IllegalStateException("The memory store has not been initialized yet. Make sure to load data before.")
        }
        return value ?: throw IllegalArgumentException("Translation key doesn't exist")
    }
}