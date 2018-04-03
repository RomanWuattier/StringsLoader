package com.romanwuattier.loader.data

import com.romanwuattier.loader.converter.ConverterType
import java.io.File

/**
 * A [LoadRequest] consists in:
 * <ul>
 *     <li>Loading data from a specific [url]</li>
 *     <li>Deserializing data in a [ConcurrentHashMap] according to a [ConverterType]</li>
 * </ul>
 */
data class LoadRequest(val url: String, val cacheDir: File, val converterType: ConverterType)