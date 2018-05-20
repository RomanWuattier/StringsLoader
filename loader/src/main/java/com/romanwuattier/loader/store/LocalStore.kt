package com.romanwuattier.loader.store

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.data.Request
import java.util.concurrent.ConcurrentHashMap

internal class LocalStore<K, V> : Store.Local<K, V> {

    override fun fetch(loadRequest: Request, callback: LoaderCallback) {
    }

    override fun onSuccess(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
    }

    override fun onError(throwable: Throwable, callback: LoaderCallback) {
    }

    private fun updateMemoryStore(map: ConcurrentHashMap<K, V>, callback: LoaderCallback) {
    }
}