package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType


interface Loader {

    fun <K, V> load(url: String, converterType: ConverterType, callback: LoaderCallback)

    @Throws(IllegalStateException::class)
    fun <K, V> reload(callback: LoaderCallback)

    fun <K, V> get(key: K): V?

    fun <K, V> clear(): Boolean
}