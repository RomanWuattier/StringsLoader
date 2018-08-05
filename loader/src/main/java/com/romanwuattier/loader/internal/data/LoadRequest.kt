package com.romanwuattier.loader.internal.data

import android.content.Context
import com.romanwuattier.loader.ConverterType
import java.io.File

/**
 * Marker interface defining a [RemoteRequest], a [LocalRequest]
 */
internal interface Request

/**
 * A [RemoteRequest] consists in:
 * <ul>
 *     <li>Loading data from a specific remote [url]</li>
 *     <li>Deserializing data in a [ConcurrentHashMap] according to a [ConverterType]</li>
 * </ul>
 */
internal data class RemoteRequest(val url: String, val cacheDir: File, val converterType: ConverterType) : Request

/**
 * A [LocalRequest] consists in:
 * <ul>
 *     <li>Loading data from a specific file [path]</li>
 *     <li>Deserializing data in a [ConcurrentHashMap] according to a [ConverterType]</li>
 * </ul>
 */
internal data class LocalRequest(val context: Context, val path: String, val converterType: ConverterType) : Request
