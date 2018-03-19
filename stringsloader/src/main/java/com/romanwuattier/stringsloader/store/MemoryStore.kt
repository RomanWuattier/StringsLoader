package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.LoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal class MemoryStore : Store, Store.Memory {

    internal companion object Provider {
        @Volatile
        private var instance: MemoryStore? = null

        @Synchronized
        fun provideInstance(): MemoryStore {
            if (instance == null) {
                instance = MemoryStore()
            }
            return instance as MemoryStore
        }
    }

    internal lateinit var memoryMap: ConcurrentHashMap<*, *>

    override fun hasBeenInitialized(): Boolean = ::memoryMap.isInitialized

    override fun isEmpty(): Boolean = memoryMap.isEmpty()

    override fun <K, V> fetch(request: LoadRequest) {
        onSuccess(memoryMap, request.callback)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K, V> get(key: K): V? = memoryMap[key] as? V?

    override fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError()
    }
}