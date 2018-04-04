package com.romanwuattier.stringsloader

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread
import java.io.File

/**
 * A static object that provides all the library methods to retrieve [String] from [Any] key
 */
object StringsLoader {

    private val EMPTY = ""

    private val loader = StringsLoaderModule.provideInstance().getAnyLoader()

    @Synchronized
    fun load(url: String, cacheDir: File, converterType: ConverterType, callback: LoaderCallback) {
        checkMainThread()

        loader.load<Any, String>(url, cacheDir, converterType, callback)
    }

    @Synchronized
    fun reload(callback: LoaderCallback) {
        checkMainThread()

        loader.reload<Any, String>(callback)
    }

    fun get(key: Any): String {
        checkMainThread()

        return loader.get<Any, String>(key) ?: EMPTY
    }

    @Synchronized
    fun clear(): Boolean {
        checkMainThread()

        return loader.clear<Any, String>()
    }

}