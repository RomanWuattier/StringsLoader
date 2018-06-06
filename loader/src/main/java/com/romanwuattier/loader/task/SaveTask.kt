package com.romanwuattier.loader.task

import com.romanwuattier.loader.store.Store
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

/**
 * The [Callable] task to save remote data in the [Store.Memory]
 */
internal class SaveTask<K, V>(private val map: ConcurrentHashMap<K, V>,
    private val memoryStore: Store.Memory<K, V>) : Callable<Boolean> {

    override fun call(): Boolean {
        memoryStore.putAll(map)
        return true
    }
}
