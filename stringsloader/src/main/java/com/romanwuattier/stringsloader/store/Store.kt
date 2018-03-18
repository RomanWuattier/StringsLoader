package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.StringsLoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

interface Store {

    interface Remote {
        fun <K, V> fetch(request: LoadRequest,
                  onSuccess: (ConcurrentHashMap<K, V>, StringsLoaderCallback) -> Unit,
                  onError: (Throwable, StringsLoaderCallback) -> Unit)
    }

    interface Memory {
        fun hasBeenInitialized(): Boolean

        fun isEmpty(): Boolean

        fun <K, V> get(key: K): V?
    }
}