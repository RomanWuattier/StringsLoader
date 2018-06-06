package com.romanwuattier.loader

import android.content.Context
import com.romanwuattier.loader.converter.ConverterType
import java.io.File


interface Loader {

    /**
     * Expose an asynchronously loading method to other libraries. When the data is loaded the [callback] is triggered.
     * Load data from the [Store.Remote] according to the policy.
     */
    fun <K, V> loadFromRemote(url: String, cacheDir: File, converterType: ConverterType, callback: LoaderCallback)

    /**
     * Expose an asynchronously loading method to other libraries. When the data is loaded the [callback] is triggered.
     * Load data from the [Store.Local] according to the policy.
     */
    fun <K, V> loadFromLocal(path: String, context: Context, converterType: ConverterType, callback: LoaderCallback)

    /**
     * Get a value [V] from the [Store.Memory].
     *
     * @return null when the key [K] is not found, the [Store.Memory] is empty or has not been initialized
     */
    fun <K, V> get(key: K): V?

    /**
     * Clear the [Store.Memory].
     *
     * @return true when the store has been cleared, false otherwise
     */
    fun <K, V> clear(): Boolean
}
