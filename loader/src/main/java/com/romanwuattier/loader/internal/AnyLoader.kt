package com.romanwuattier.loader.internal

import android.content.Context
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.ConverterType
import com.romanwuattier.loader.internal.data.LocalRequest
import com.romanwuattier.loader.internal.data.RemoteRequest
import com.romanwuattier.loader.internal.data.Request
import com.romanwuattier.loader.internal.store.Store
import com.romanwuattier.loader.internal.utils.checkMainThread
import java.io.File

internal class AnyLoader private constructor() : Loader {

    companion object Provider {
        @Volatile
        private var instance: AnyLoader? = null

        @Synchronized
        fun provideInstance(): AnyLoader {
            if (instance == null) {
                instance = AnyLoader()
            }
            return instance as AnyLoader
        }
    }

    private val module: LoaderModule by lazy { LoaderModule.provideInstance() }

    @Synchronized
    override fun <K, V> loadFromRemote(url: String, cacheDir: File, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        val remoteRequest = RemoteRequest(url, cacheDir, converterType)
        val store = module.getRemoteStore<K, V>()
        load(remoteRequest, store, callback)
    }

    @Synchronized
    override fun <K, V> loadFromLocal(path: String, context: Context, converterType: ConverterType,
        callback: LoaderCallback) {
        checkMainThread()

        val localRequest = LocalRequest(context, path, converterType)
        val store = module.getLocalStore<K, V>()
        load(localRequest, store, callback)
    }

    private fun <K, V> load(request: Request, store: Store.AsyncStore<K, V>, callback: LoaderCallback) {
        store.fetch(request, callback)
    }

    override fun <K, V> get(key: K): V? {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.get(key)
    }

    @Synchronized
    override fun <K, V> clear(): Boolean {
        checkMainThread()

        val store = module.getMemoryStore<K, V>()
        return store.clear()
    }
}
