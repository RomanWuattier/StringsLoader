package com.romanwuattier.loader.filereader

import com.romanwuattier.loader.converter.ConverterFactory
import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread

internal class FileReaderModule private constructor() {

    companion object Provider {
        @Volatile
        private var instance: FileReaderModule? = null

        @Synchronized
        fun provideInstance(): FileReaderModule {
            checkMainThread()

            if (instance == null) {
                instance = FileReaderModule()
            }
            return instance as FileReaderModule
        }
    }

    private val converterFactory = ConverterFactory()

    @Synchronized
    internal fun getConverterStrategy(type: ConverterType): ConverterStrategy = converterFactory.getConverter(type)
}
