package com.romanwuattier.loader.internal.converter

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import java.io.Reader
import java.util.concurrent.ConcurrentHashMap

internal interface ConverterStrategy {

    /**
     * Deserialize data from the specified [reader] into a [ConcurrentHashMap]
     *
     * @param K Type of the key
     * @param V Type of the value
     * @property reader The character stream reader
     * @return A [ConcurrentHashMap] of key [K] and value [V]
     *
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <K, V> convert(reader: Reader?): ConcurrentHashMap<K, V>
}
