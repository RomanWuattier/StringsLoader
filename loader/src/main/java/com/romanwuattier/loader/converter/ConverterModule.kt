package com.romanwuattier.loader.converter

import com.romanwuattier.loader.utils.checkMainThread

internal class ConverterModule private constructor() {

    internal companion object Provider {
        @Volatile
        private var instance: ConverterModule? = null

        @Synchronized
        fun provideInstance(): ConverterModule {
            checkMainThread()

            if (instance == null) {
                instance = ConverterModule()
            }
            return instance as ConverterModule
        }
    }

    @Synchronized
    internal fun getJsonConverter() = JsonConverter()

    @Synchronized
    internal fun getOtherConverter() = OtherConverter()
}