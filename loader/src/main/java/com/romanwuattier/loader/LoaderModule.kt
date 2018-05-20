package com.romanwuattier.loader

import com.romanwuattier.loader.store.Store
import com.romanwuattier.loader.store.StoreModule
import com.romanwuattier.loader.utils.checkMainThread

/**
 * A Singleton class that provides all the dependencies to the classes of the [Loader] library
 */
internal class LoaderModule private constructor() {

    companion object Provider {
        @Volatile
        private var instance: LoaderModule? = null

        @Synchronized
        fun provideInstance(): LoaderModule {
            checkMainThread()

            if (instance == null) {
                instance = LoaderModule()
            }
            return instance as LoaderModule
        }
    }

    @Synchronized
    internal fun <K, V> getStorePolicy(): Store<K, V> = StoreModule.provideInstance<K, V>().getStorePolicy()

    @Synchronized
    internal fun <K, V> getRemoteStore(): Store.Remote<K, V> = StoreModule.provideInstance<K, V>().getRemoteStore()

    @Synchronized
    internal fun <K, V> getMemoryStore(): Store.Memory<K, V> = StoreModule.provideInstance<K, V>().getMemoryStore()
}
