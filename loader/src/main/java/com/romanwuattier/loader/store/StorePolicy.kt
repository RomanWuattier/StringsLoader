package com.romanwuattier.loader.store

internal class StorePolicy {

    internal fun <K, V> defineStorePolicy(): Store<K, V> {
        val memoryStore = createMemoryStore<K, V>()
        val remoteStore = createRemoteStore<K, V>()

        return if (!memoryStore.hasBeenInitialized() || memoryStore.isEmpty()) {
            remoteStore
        } else {
            memoryStore
        }
    }

    private fun <K, V> createMemoryStore() = StoreModule.provideInstance<K, V>().getMemoryStore()

    private fun <K, V> createRemoteStore() = StoreModule.provideInstance<K, V>().getRemoteStore()
}