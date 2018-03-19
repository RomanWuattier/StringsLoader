package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.LoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

interface Store {

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