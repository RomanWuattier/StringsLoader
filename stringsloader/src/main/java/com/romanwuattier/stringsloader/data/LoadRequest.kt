package com.romanwuattier.stringsloader.data

import com.romanwuattier.stringsloader.StringsLoaderCallback
import com.romanwuattier.stringsloader.converter.ConverterType

data class LoadRequest(val url: String, val converterType: ConverterType, val callback: StringsLoaderCallback)