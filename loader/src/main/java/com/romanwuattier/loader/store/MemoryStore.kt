package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal class MemoryStore<K, V> : Store.Memory<K, V> {

    private lateinit var memoryMap: ConcurrentHashMap<K, V>

    override fun hasBeenInitialized(): Boolean = ::memoryMap.isInitialized

    override fun isEmpty(): Boolean = memoryMap.isEmpty()

    override fun fetch(loadRequest: LoadRequest) {
        if (hasBeenInitialized()) {
            onSuccess(memoryMap, loadRequest.callback)
        } else {
            onError(IllegalStateException("Map shouldn't be empty"), loadRequest.callback)
        }
    }

    override fun get(key: K): V? = memoryMap[key]

    override fun putAll(map: ConcurrentHashMap<K, V>) {
        if (!hasBeenInitialized()) {
            memoryMap = map
        } else {
            memoryMap.putAll(map)
        }
    }

    override fun clear(): Boolean = if (hasBeenInitialized()) {
        memoryMap.clear()
        isEmpty()
    } else {
        false
    }

    override fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
        callback.onComplete()
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
        callback.onError(throwable)
    }
}