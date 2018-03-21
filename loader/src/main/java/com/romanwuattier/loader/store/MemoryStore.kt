package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal class MemoryStore<K, V> private constructor() : Store.Memory<K, V> {

    internal companion object Provider: Store.GenericProvider {
        @Volatile
        private var instance: Store.Memory<*, *>? = null

        @Suppress("UNCHECKED_CAST")
        @Synchronized
        override fun <K, V> provideInstance(): Store.Memory<K, V> {
            if (instance == null) {
                instance = MemoryStore<K, V>()
            }
            return instance as Store.Memory<K, V>
        }
    }

    private lateinit var memoryMap: ConcurrentHashMap<K, V>

    override fun hasBeenInitialized(): Boolean = ::memoryMap.isInitialized

    override fun isEmpty(): Boolean = memoryMap.isEmpty()

    override fun fetch(request: LoadRequest) {
        onSuccess(memoryMap, request.callback)
    }

    override fun get(key: K): V? = memoryMap[key]

    override fun putAll(map: ConcurrentHashMap<K, V>) {
        if (!hasBeenInitialized()) {
            memoryMap = map
        } else {
            memoryMap.putAll(map)
        }
    }

    override fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }
}