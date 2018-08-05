package com.romanwuattier.loader.internal.store

import com.romanwuattier.loader.internal.utils.checkMainThread

/**
 * A Singleton generic class that provides all the dependencies liked to the [Store]
 */
internal class StoreModule<K, V> private constructor() {

    internal companion object Provider : GenericProvider {
        @Volatile
        private var instance: StoreModule<*, *>? = null

        @Suppress("UNCHECKED_CAST")
        @Synchronized
        override fun <K, V> provideInstance(): StoreModule<K, V> {
            checkMainThread()

            if (instance == null) {
                instance = StoreModule<K, V>()
            }
            return instance as StoreModule<K, V>
        }
    }

    private val memoryStore: Store.Memory<K, V> by lazy { MemoryStore<K, V>() }

    private val remoteStore: Store.Remote<K, V> by lazy { RemoteStore<K, V>() }

    private val localStore: Store.Local<K, V> by lazy { LocalStore<K, V>() }

    @Synchronized
    internal fun getMemoryStore(): Store.Memory<K, V> = memoryStore

    @Synchronized
    internal fun getRemoteStore(): Store.Remote<K, V> = remoteStore

    @Synchronized
    internal fun getLocalStore(): Store.Local<K, V> = localStore
}

private interface GenericProvider {
    fun <K, V> provideInstance(): StoreModule<K, V>
}
