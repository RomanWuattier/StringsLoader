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
    internal fun getOkHttpClient(defaultCacheDir: File): OkHttpClient = provider.buildOkHttpClient(defaultCacheDir)

    @Synchronized
    internal fun getRequest(url: String): Request = provider.buildOkHttpRequest(url)

    @Synchronized
    internal fun getConverterStrategy(type: ConverterType): ConverterStrategy = converterFactory.getConverter(type)
}