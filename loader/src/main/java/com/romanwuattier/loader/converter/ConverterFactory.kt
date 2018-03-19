package com.romanwuattier.loader.converter

internal class ConverterFactory {

    companion object Factory {
        internal fun getConverter(type: ConverterType): ConverterStrategy = when (type) {
            ConverterType.JSON -> createJsonConverter()
            else -> createOtherConverter()
        }

        private fun createJsonConverter() = JsonConverter()

        private fun createOtherConverter() = OtherConverter()
    }
}