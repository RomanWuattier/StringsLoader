package com.romanwuattier.stringsloader.data

import com.romanwuattier.stringsloader.LoaderCallback
import com.romanwuattier.stringsloader.converter.ConverterType

data class LoadRequest(val url: String, val converterType: ConverterType, val callback: LoaderCallback)