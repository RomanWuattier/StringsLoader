package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.Request
import java.util.concurrent.ConcurrentHashMap

/**
 * Interface every kind of storage
 * <ul>
 *     <li>Remote</li>
 *     <li>Local</li>
 *     <li>Memory</li>
 * </ul>
 *
 * Note: this interface shouldn't be directly implemented, {@see Store.Remote} and {@see Store.Memory} interfaces
 */
internal interface Store<K, V> {

    /**
     * Asynchronous [Store] representation
     */
    interface AsyncStore<K, V> : Store<K, V> {
        /**
         * Fetch data according to [LoadRequest] specification
         *
         * [loadRequest] Loading specification
         * [callback] Asynchronous Callback
         */
        fun fetch(loadRequest: Request, callback: LoaderCallback)

        /**
         * Trigger [LoaderCallback.onComplete] method when [fetch]ing, deserializing and storing in memory are succeed
         */
        fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback)

        /**
         * Trigger [LoaderCallback.onError] method when an error occurred while fetching, deserializing or storing in
         * memory
         */
        fun onError(throwable: Throwable, callback: LoaderCallback)
    }

    /**
     * Remote [Store] representation
     */
    interface Remote<K, V> : AsyncStore<K, V>

    /**
     * Local [Store] representation
     */
    interface Local<K, V> : AsyncStore<K, V>

    /**
     * Memory [Store] representation
     * Manage the [ConcurrentHashMap] representing the [MemoryStore]
     */
    interface Memory<K, V> : Store<K, V> {
        fun hasBeenInitialized(): Boolean

        fun isEmpty(): Boolean

        fun get(key: K): V?

        fun putAll(map: ConcurrentHashMap<K, V>)

        fun clear(): Boolean
    }
}
