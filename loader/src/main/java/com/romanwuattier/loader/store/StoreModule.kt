package com.romanwuattier.loader.store

import com.romanwuattier.loader.utils.checkMainThread

internal class StoreModule private constructor() {

    internal companion object Provider {
        @Volatile
        private var instance: StoreModule? = null

        @Synchronized
        fun provideInstance(): StoreModule {
            checkMainThread()

            if (instance == null) {
                instance = StoreModule()
            }
            return instance as StoreModule
        }
    }

    @Synchronized
    internal fun getMemoryStore() = MemoryStore.provideInstance()

    @Synchronized
    internal fun getRemoteStore() = RemoteStore.provideInstance()
}