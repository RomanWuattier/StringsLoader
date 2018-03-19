package com.romanwuattier.stringsloader.converter

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import java.io.Reader
import java.util.concurrent.ConcurrentHashMap

interface ConverterStrategy {

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <K, V> convert(reader: Reader?): ConcurrentHashMap<K, V>
}