package com.romanwuattier.loader.data

import com.romanwuattier.loader.converter.ConverterType
import java.io.File

internal interface Request

/**
 * A [RemoteRequest] consists in:
 * <ul>
 *     <li>Loading data from a specific [url]</li>
 *     <li>Deserializing data in a [ConcurrentHashMap] according to a [ConverterType]</li>
 * </ul>
 */
internal data class RemoteRequest(val url: String, val cacheDir: File, val converterType: ConverterType) : Request

internal data class LocalRequest(val path: String, val converterType: ConverterType) : Request
