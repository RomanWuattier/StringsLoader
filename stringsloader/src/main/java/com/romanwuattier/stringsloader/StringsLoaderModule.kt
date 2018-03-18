package com.romanwuattier.stringsloader

import com.romanwuattier.stringsloader.converter.ConverterFactory
import com.romanwuattier.stringsloader.converter.ConverterStrategy
import com.romanwuattier.stringsloader.converter.ConverterType
import com.romanwuattier.stringsloader.store.StorePolicy
import okhttp3.OkHttpClient

internal class StringsLoaderModule {

    companion object Provider {
        @Volatile
        private var instance: StringsLoaderModule? = null

        @Synchronized
        fun provideInstance(): StringsLoaderModule {
            if (instance == null) {
                instance = StringsLoaderModule()
            }
            return instance as StringsLoaderModule
        }
    }

    private val okHttpClient = OkHttpClient()

    private val storePolicy = StorePolicy()

    @Synchronized
    fun getOkHttpClient(): OkHttpClient = okHttpClient

    @Synchronized
    fun getConverterStrategy(type: ConverterType): ConverterStrategy = ConverterFactory.getConverter(type)

    @Synchronized
    fun getStorePolicy() = storePolicy
}