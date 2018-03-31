package com.romanwuattier.stringsloader

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread

/**
 * A Singleton class that provides all the library methods to retrieve [String] from [Any] key
 */
class StringsLoader private constructor() {

    companion object Provider {
        @Volatile
        private var instance: StringsLoader? = null

        @Synchronized
        fun provideInstance(): StringsLoader {
            checkMainThread()

            if (instance == null) {
                instance = StringsLoader()
            }
            return instance as StringsLoader
        }
    }

    private val EMPTY = ""

    private val module = StringsLoaderModule.provideInstance()

    @Synchronized
    fun load(url: String, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        val loader = module.getAnyLoader()
        loader.load<Any, String>(url, converterType, callback)
    }

    @Synchronized
    fun reload(callback: LoaderCallback) {
        checkMainThread()

        val loader = module.getAnyLoader()
        loader.reload<Any, String>(callback)
    }

    fun get(key: Any): String {
        checkMainThread()

        val loader = module.getAnyLoader()
        return loader.get<Any, String>(key) ?: EMPTY
    }

    @Synchronized
    fun clear(): Boolean {
        checkMainThread()

        val loader = module.getAnyLoader()
        return loader.clear<Any, String>()
    }
}