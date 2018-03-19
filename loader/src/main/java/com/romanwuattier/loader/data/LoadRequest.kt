package com.romanwuattier.loader.data

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType

data class LoadRequest(val url: String, val converterType: ConverterType, val callback: LoaderCallback)