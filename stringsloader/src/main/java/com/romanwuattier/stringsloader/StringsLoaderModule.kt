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

    @Synchronized
    fun getAnyLoader(): Loader = AnyLoader.provideInstance()
}