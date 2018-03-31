package com.romanwuattier.loader.store

import com.romanwuattier.loader.utils.checkMainThread

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

    private val memoryStore: Store.Memory<K, V> = MemoryStore()

    private val remoteStore: Store.Remote<K, V> = RemoteStore()

    @Synchronized
    internal fun getMemoryStore(): Store.Memory<K, V> = memoryStore

    @Synchronized
    internal fun getRemoteStore(): Store.Remote<K, V> = remoteStore
}

private interface GenericProvider {
    fun <K, V> provideInstance(): StoreModule<K, V>
}