package com.romanwuattier.loader

import com.romanwuattier.loader.converter.ConverterType


interface Loader {

    /**
     * Expose an asynchronously loading method to other libraries. When the data is loaded the [callback] is triggered.
     * Load data from the [Store.Remote] or the [Store.Memory] according to the policy.
     */
    fun <K, V> load(url: String, converterType: ConverterType, callback: LoaderCallback)

    /**
     * Expose an asynchronously reloading method to other libraries. When the data is loaded the [callback] is
     * triggered.
     * Load data from the [Store.Remote].
     *
     * @throws IllegalStateException when the [LoadRequest] has not been initialized first. If this exception is
     * triggered then use [load] method first.
     */
    @Throws(IllegalStateException::class)
    fun <K, V> reload(callback: LoaderCallback)

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