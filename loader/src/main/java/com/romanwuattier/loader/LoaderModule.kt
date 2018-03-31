package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterFactory
import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.store.Store
import com.romanwuattier.loader.store.StoreModule
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

    private val converterFactory = ConverterFactory()

    private val storePolicy = StorePolicy()

    @Synchronized
    internal fun getOkHttpClient(): OkHttpClient = okHttpClient

    @Synchronized
    internal fun getConverterStrategy(type: ConverterType): ConverterStrategy = converterFactory.getConverter(type)

    @Synchronized
    internal fun <K, V> getStorePolicy(): Store<K, V> = storePolicy.defineStorePolicy()

    @Synchronized
    internal fun <K, V> getRemoteStore(): Store.Remote<K, V> = StoreModule.provideInstance<K, V>().getRemoteStore()

    @Synchronized
    internal fun <K, V> getMemoryStore(): Store.Memory<K, V> = StoreModule.provideInstance<K, V>().getMemoryStore()
}