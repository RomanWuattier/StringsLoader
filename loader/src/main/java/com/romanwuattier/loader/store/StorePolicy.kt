package com.romanwuattier.loader.store

internal class StorePolicy {

    /**
     * Define the library store policy. When the [Store.Memory] is empty or has not been initialized then
     * the [Store.Remote] is provided, the [Store.Memory] is provided otherwise.
     */
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
