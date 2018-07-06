package com.romanwuattier.loader.internal.converter

import com.romanwuattier.loader.ConverterType

internal class ConverterFactory {

    /**
     * Provide the right [ConverterStrategy] according to the [type] parameter
     *
     * @property type Specific [ConverterType]
     * @return A new instance of [JsonConverter] when the [type] is [ConverterType.JSON], a new instance of
     * [OtherConverter] otherwise
     */
    internal fun getConverter(type: ConverterType): ConverterStrategy = when (type) {
        ConverterType.JSON -> createJsonConverter()
        else -> createOtherConverter()
    }

    private fun createJsonConverter() = ConverterModule.provideInstance().getJsonConverter()

    private fun createOtherConverter() = ConverterModule.provideInstance().getOtherConverter()
}
