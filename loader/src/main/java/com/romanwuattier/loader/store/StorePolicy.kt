package com.romanwuattier.loader.store

internal class StorePolicy {

    internal companion object HandleStorePolicy {
        private val memoryStore = MemoryStore.provideInstance()
        private val remoteStore = RemoteStore.provideInstance()

        internal fun defineStorePolicy(): Store = if (!memoryStore.hasBeenInitialized() || memoryStore.isEmpty()) {
            remoteStore
        } else {
            memoryStore
        }
    }
}