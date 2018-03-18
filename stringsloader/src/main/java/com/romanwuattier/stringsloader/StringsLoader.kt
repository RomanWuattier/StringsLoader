package com.romanwuattier.stringsloader

import com.romanwuattier.stringsloader.converter.ConverterType
import com.romanwuattier.stringsloader.data.LoadRequest
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
    override fun load(url: String, converterType: ConverterType, callback: StringsLoaderCallback) {
        checkMainThread()

        val request = LoadRequest(url, converterType, callback)
        val policy = StringsLoaderModule.provideInstance().getStorePolicy()
        policy.fetch<Any, String>(request)
    }

    override fun get(key: Any): String {
        checkMainThread()

        val policy = StringsLoaderModule.provideInstance().getStorePolicy()
        val value = policy.get<Any, String>(key)
        return value ?: throw IllegalArgumentException("Translation key doesn't exist")
    }
}