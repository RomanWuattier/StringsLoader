package com.romanwuattier.loader.internal.task

import android.net.TrafficStats
import com.romanwuattier.loader.internal.converter.ConverterStrategy
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

    private val SOCKET_TAG = "strings-loader".hashCode()

    override fun call(): ConcurrentHashMap<K, V> {
        var response: Response? = null

        return try {
            TrafficStats.setThreadStatsTag(SOCKET_TAG)
            response = okHttpClient.newCall(okHttpRequest).execute()
            converter.convert(reader = response?.body()?.charStream())
        } finally {
            response?.close()
        }
    }
}
