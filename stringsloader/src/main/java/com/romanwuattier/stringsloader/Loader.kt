package com.romanwuattier.stringsloader

import com.romanwuattier.stringsloader.converter.ConverterType

interface Loader {

    fun load(url: String, converterType: ConverterType, callback: LoaderCallback)

    fun get(key: Any): String
}