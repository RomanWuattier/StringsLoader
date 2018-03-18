package com.romanwuattier.stringsloader.converter

import java.io.Reader
import java.util.concurrent.ConcurrentHashMap

internal class OtherConverter : ConverterStrategy {

    override fun <K, V> convert(reader: Reader?): ConcurrentHashMap<K, V> = ConcurrentHashMap()
}