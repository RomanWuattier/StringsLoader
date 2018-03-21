package com.romanwuattier.loader

import com.romanwuattier.loader.store.Store
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

internal class SaveTask<K, V>(private val map: ConcurrentHashMap<K, V>,
    private val memoryStore: Store.Memory<K, V>) : Callable<Boolean> {

    override fun call(): Boolean {
        memoryStore.putAll(map)
        return true
    }
}