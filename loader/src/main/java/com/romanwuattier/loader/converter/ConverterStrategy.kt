package com.romanwuattier.loader.converter

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import java.io.Reader
import java.util.concurrent.ConcurrentHashMap

internal interface ConverterStrategy {

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <K, V> convert(reader: Reader?): ConcurrentHashMap<K, V>
}