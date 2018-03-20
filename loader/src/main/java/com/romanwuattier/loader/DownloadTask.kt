package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.data.LoadRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

internal class DownloadTask<K, V>(private val loadRequest: LoadRequest, private val okHttpClient: OkHttpClient,
    private val converter: ConverterStrategy) : Callable<ConcurrentHashMap<K, V>> {

    override fun call(): ConcurrentHashMap<K, V> {
        var response: Response? = null

        try {
            val request = Request.Builder().url(loadRequest.url).build()
            response = okHttpClient.newCall(request).execute()
            return converter.convert(reader = response?.body()?.charStream())
        } finally {
            response?.close()
        }
    }
}