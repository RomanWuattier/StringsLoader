package com.romanwuattier.loader.data

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType

/**
 * A [LoadRequest] consists in:
 * <ul>
 *     <li>Loading data from a specific [url]</li>
 *     <li>Deserializing data in a [ConcurrentHashMap] according to a [ConverterType]</li>
 *     <li>Trigger the success callback when loading and Deserializing went well, trigger the error callback otherwise</li>
 * </ul>
 */
data class LoadRequest(val url: String, val converterType: ConverterType, val callback: LoaderCallback)