package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal interface Store<K, V> {

    fun fetch(request: LoadRequest)

    fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback)

    fun onError(throwable: Throwable, callback: LoaderCallback)

    interface Remote<K, V> : Store<K, V>

    interface Memory<K, V> : Store<K, V> {
        fun hasBeenInitialized(): Boolean

        fun isEmpty(): Boolean

        fun get(key: K): V?

        fun putAll(map: ConcurrentHashMap<K, V>)

        fun clear(): Boolean
    }
}