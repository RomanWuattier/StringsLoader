package com.romanwuattier.loader.converter

import com.google.gson.GsonBuilder
import java.io.Reader
import java.util.concurrent.ConcurrentHashMap

internal class JsonConverter : ConverterStrategy {

    private val gson = GsonBuilder().create()

    override fun <K, V> convert(reader: Reader?): ConcurrentHashMap<K, V> = try {
        val type = ConcurrentHashMap<K, V>().javaClass
        gson.fromJson(reader, type)
    } finally {
        reader?.close()
    }
}