package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal class MemoryStore : Store.Memory {

    internal companion object Provider {
        @Volatile
        private var instance: Store.Memory? = null

        @Synchronized
        fun provideInstance(): Store.Memory {
            if (instance == null) {
                instance = MemoryStore()
            }
            return instance as Store.Memory
        }
    }

    // TODO: Find a way to initialize <K, V> ConcurrentHashMap
    private lateinit var memoryMap: ConcurrentHashMap<*, *>

    override fun hasBeenInitialized(): Boolean = ::memoryMap.isInitialized

    override fun isEmpty(): Boolean = memoryMap.isEmpty()

    override fun <K, V> fetch(request: LoadRequest) {
        onSuccess(memoryMap, request.callback)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K, V> get(key: K): V? = memoryMap[key] as? V?

    override fun <K, V> putAll(map: ConcurrentHashMap<K, V>) {
        memoryMap = map
    }

    override fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }
}