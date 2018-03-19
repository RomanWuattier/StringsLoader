package com.romanwuattier.stringsloader

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread

class StringsLoader private constructor() {

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

    private val module = StringsLoaderModule.provideInstance()

    @Synchronized
    fun load(url: String, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        val loader = module.getAnyLoader()
        loader.load<Any, String>(url, converterType, callback)
    }

    fun get(key: Any): String {
        checkMainThread()

        val loader = module.getAnyLoader()
        return loader.get<Any, String>(key)
    }
}