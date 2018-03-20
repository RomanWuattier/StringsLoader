package com.romanwuattier.loader.store

internal class StorePolicy {

    internal companion object HandleStorePolicy {
        private val memoryStore = StoreModule.provideInstance().getMemoryStore()
        private val remoteStore = StoreModule.provideInstance().getRemoteStore()

        internal fun defineStorePolicy(): Store = if (!memoryStore.hasBeenInitialized() || memoryStore.isEmpty()) {
            remoteStore
        } else {
            memoryStore
        }
    }
}