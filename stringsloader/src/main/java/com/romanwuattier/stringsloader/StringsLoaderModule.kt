package com.romanwuattier.stringsloader

import com.romanwuattier.loader.AnyLoader
import com.romanwuattier.loader.Loader

internal class StringsLoaderModule {

    companion object Provider {
        @Volatile
        private var instance: StringsLoaderModule? = null

        @Synchronized
        fun provideInstance(): StringsLoaderModule {
            if (instance == null) {
                instance = StringsLoaderModule()
            }
            return instance as StringsLoaderModule
        }
    }

    private val anyLoader: Loader = AnyLoader.provideInstance()

    @Synchronized
    fun getAnyLoader(): Loader = anyLoader
}