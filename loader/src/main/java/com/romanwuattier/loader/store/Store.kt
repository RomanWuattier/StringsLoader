package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal interface Store {

    fun <K, V> fetch(request: LoadRequest)

    fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback)

    fun onError(throwable: Throwable, callback: LoaderCallback)

    interface Remote

    interface Memory {
        fun hasBeenInitialized(): Boolean

        fun isEmpty(): Boolean

        fun <K, V> get(key: K): V?
    }
}