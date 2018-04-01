package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterStrategy
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

/**
 * The [Callable] task to download data from [okHttpRequest]
 */
internal class DownloadTask<K, V>(private val okHttpClient: OkHttpClient, private val okHttpRequest: Request,
    private val converter: ConverterStrategy) : Callable<ConcurrentHashMap<K, V>> {

    override fun call(): ConcurrentHashMap<K, V> {
        var response: Response? = null

        return try {
            response = okHttpClient.newCall(okHttpRequest).execute()
            converter.convert(reader = response?.body()?.charStream())
        } finally {
            response?.close()
        }
    }
}