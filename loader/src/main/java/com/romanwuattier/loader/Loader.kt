package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType


interface Loader {

    fun <K, V> load(url: String, converterType: ConverterType, callback: LoaderCallback)

    fun <K, V> get(key: K): V
}