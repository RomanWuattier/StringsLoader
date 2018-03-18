package com.romanwuattier.stringsloader.store

import java.util.concurrent.ConcurrentHashMap

class MemoryStore : Store.Memory {

    companion object Provider {
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

    override fun <K, V> get(key: K): V? = memoryMap[key] as? V?
}