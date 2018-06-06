package com.romanwuattier.loader.task

import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.data.LocalRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

internal class FileReaderTask<K, V>(private val localRequest: LocalRequest,
    private val converter: ConverterStrategy) : Callable<ConcurrentHashMap<K, V>> {

    override fun call(): ConcurrentHashMap<K, V> {
        val assetManager = localRequest.context.assets
        val reader = BufferedReader(InputStreamReader(assetManager.open(localRequest.path)))
        return converter.convert(reader = reader)
    }
}
