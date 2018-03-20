package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterFactory
import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.store.Store
import com.romanwuattier.loader.store.StorePolicy
import com.romanwuattier.loader.utils.checkMainThread
import okhttp3.OkHttpClient

internal class LoaderModule private constructor() {

    companion object Provider {
        @Volatile
        private var instance: LoaderModule? = null

        @Synchronized
        fun provideInstance(): LoaderModule {
            checkMainThread()

            if (instance == null) {
                instance = LoaderModule()
            }
            return instance as LoaderModule
        }
    }

    private val okHttpClient = OkHttpClient()

    @Synchronized
    internal fun getOkHttpClient(): OkHttpClient = okHttpClient

    @Synchronized
    internal fun getConverterStrategy(type: ConverterType): ConverterStrategy = ConverterFactory.getConverter(type)

    @Synchronized
    internal fun getStorePolicy(): Store = StorePolicy.defineStorePolicy()
}