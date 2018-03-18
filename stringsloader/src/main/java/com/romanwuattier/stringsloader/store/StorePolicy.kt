package com.romanwuattier.stringsloader.store

import com.romanwuattier.stringsloader.StringsLoaderCallback
import com.romanwuattier.stringsloader.data.LoadRequest
import java.util.concurrent.ConcurrentHashMap

internal class StorePolicy {

    private val memoryStore = MemoryStore.provideInstance()
    private val remoteStore = RemoteStore.provideInstance()

    internal fun <K, V> fetch(request: LoadRequest) {
        if (!memoryStore.hasBeenInitialized()) {
            fetchFromRemote<K, V>(request)
        } else {
            if (!memoryStore.isEmpty()) {
                fetchFromMemory(request)
            } else {
                fetchFromRemote<K, V>(request)
            }
        }
    }

    private fun fetchFromMemory(request: LoadRequest) {
        request.callback.onComplete()
    }

    private fun <K, V> fetchFromRemote(request: LoadRequest) {
        remoteStore.fetch<K, V>(request, ::onSuccess, ::onError)
    }

    private fun <K, V> onSuccess(map: ConcurrentHashMap<K, V>, callback: StringsLoaderCallback) {
        updateMemoryStore(map)
        callback.onComplete()
    }

    private fun onError(throwable: Throwable, callback: StringsLoaderCallback) {
        callback.onError()
    }

    private fun <K, V> updateMemoryStore(map: ConcurrentHashMap<K, V>) {
        memoryStore.memoryMap = map
    }

    internal fun <K, V> get(key: K): V? = memoryStore.get(key)
}