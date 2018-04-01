package com.romanwuattier.loader.downloader

import com.romanwuattier.loader.converter.ConverterFactory
import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class DownloaderModule private constructor() {

    companion object Provider {
        @Volatile
        private var instance: DownloaderModule? = null

        @Synchronized
        fun provideInstance(): DownloaderModule {
            checkMainThread()

            if (instance == null) {
                instance = DownloaderModule()
            }
            return instance as DownloaderModule
        }
    }

    private val provider = OkHttpProvider()

    private val converterFactory = ConverterFactory()

    @Synchronized
    internal fun getOkHttpClient(defaultCacheDir: File): OkHttpClient {
        val cache = provider.buildCache(defaultCacheDir)
        return provider.buildOkHttpClient(cache)
    }

    @Synchronized
    internal fun getRequest(url: String): Request {
        val cacheControl = provider.buildCacheControl()
        return provider.buildOkHttpRequest(url, cacheControl)
    }

    @Synchronized
    internal fun getConverterStrategy(type: ConverterType): ConverterStrategy = converterFactory.getConverter(type)
}