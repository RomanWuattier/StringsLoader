package com.romanwuattier.loader.store

internal class StorePolicy {

    internal companion object HandleStorePolicy {
        internal fun <K, V> defineStorePolicy(): Store<K, V> {
            val memoryStore = createMemoryStore<K, V>()
            val remoteStore = createRemoteStore<K, V>()

            return if (!memoryStore.hasBeenInitialized() || memoryStore.isEmpty()) {
                remoteStore
            } else {
                memoryStore
            }
        }

        private fun <K, V> createMemoryStore() = StoreModule.provideInstance().getMemoryStore<K, V>()

        private fun <K, V> createRemoteStore() = StoreModule.provideInstance().getRemoteStore<K, V>()
    }
}