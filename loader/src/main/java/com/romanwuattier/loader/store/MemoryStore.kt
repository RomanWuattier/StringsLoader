package com.romanwuattier.loader.store

import java.util.concurrent.ConcurrentHashMap

internal class MemoryStore<K, V> : Store.Memory<K, V> {

    private lateinit var memoryMap: ConcurrentHashMap<K, V>

    override fun hasBeenInitialized(): Boolean = ::memoryMap.isInitialized

    override fun isEmpty(): Boolean = memoryMap.isEmpty()

    override fun get(key: K): V? = if (!hasBeenInitialized()) null else memoryMap[key]

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

}
