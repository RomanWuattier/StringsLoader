package com.romanwuattier.loader.downloader

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

internal class OkHttpProvider {

    private val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
    private val CACHE_FILE = "loader-cache"
    private val CACHE_MAX_AGE_IN_MINUTE = 10

    internal fun buildOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder().cache(cache).build()

    internal fun buildCache(defaultCacheDir: File): Cache = Cache(
        createDefaultCacheDirectory(defaultCacheDir), CACHE_SIZE)

    internal fun buildOkHttpRequest(url: String, cacheControl: CacheControl): Request = Request.Builder()
        .url(url)
        .cacheControl(cacheControl)
        .build()

    internal fun buildCacheControl() = CacheControl.Builder().maxAge(CACHE_MAX_AGE_IN_MINUTE, TimeUnit.MINUTES).build()

    private fun createDefaultCacheDirectory(defaultCacheDir: File): File {
        val cache = File(defaultCacheDir, CACHE_FILE)
        if (!cache.exists()) {
            cache.mkdirs()
        }
        return cache
    }
}