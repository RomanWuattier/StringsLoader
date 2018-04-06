package com.romanwuattier.stringsloader

import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.loader.utils.checkMainThread
import java.io.File

/**
 * A static class that provides all the library methods to retrieve [String] from [Any] key
 */
class StringsLoader {

    companion object {
        private val loader = StringsLoaderModule.provideInstance().getAnyLoader()

        /**
         * Load asynchronously [Any] keys and [String]s value from an [url] and a [ConverterType] into a local store.
         *
         * <p>
         *     In order to be notified when the loading is successful or erroneous, the caller have to implement
         *     the *callback* interface.
         *     <pre><code>
         *         StringsLoader.load(URL, CACHE_DIR, ConverterType.JSON, object : LoaderCallback {
         *             override fun onComplete() {
         *                 // Loading is successful
         *             }
         *
         *             override fun onError() {
         *                 // An error occurred
         *             }
         *         }
         *     </code></pre>
         * </p>
         *
         * <p>
         *     The policy used to load data follows:
         *     <ul>
         *         <li> When the store has already been initialized and is not empty, then load data from the store.
         *         The callback interface is triggered immediately. </li>
         *         <li> When the store has not been initialized yet or is empty, then load the data from the remote
         *         source and update the store. </li>
         *     </ul>
         * </p>
         *
         * @param url The url hosting the data
         * @param cacheDir The absolute path to the application specific cache directory on the filesystem
         * @param converterType The converter used to deserialize
         * @param callback The interface used to propagate data
         */
        fun load(url: String, cacheDir: File, converterType: ConverterType, callback: LoaderCallback) {
            checkMainThread()

            loader.load<Any, String>(url, cacheDir, converterType, callback)
        }

        /**
         * Reload asynchronously the store. This action can only be triggered after calling the [load] method.
         * Reload will replace every exiting data in the store by the new one.
         *
         * <p>
         *     In order to be notified when the reloading is successful or erroneous, the caller have to implement
         *     the *callback* interface.
         *     <pre><code>
         *         StringsLoader.reload(object : LoaderCallback {
         *             override fun onComplete() {
         *                 // Reloading is successful
         *             }
         *
         *             override fun onError() {
         *                 // An error occurred
         *             }
         *         }
         *     </code></pre>
         * </p>
         *
         * @param callback The interface used to propagate data
         *
         * @see load
         */
        fun reload(callback: LoaderCallback) {
            checkMainThread()

            loader.reload<Any, String>(callback)
        }

        /**
         * Find the given [key] in the store and retrieve its corresponding [String] value. When the [key] is not
         * found, an empty [String] is returned.
         *
         * <p>
         *     <pre><code> val value = StringsLoader.get("key") </code></pre>
         * </p>
         *
         * @param key The key associating to the [String] value
         *
         * @return An empty [String] when the store doesn't contain the key, the [String] value otherwise
         */
        fun get(key: Any): String {
            checkMainThread()

            return loader.get<Any, String>(key) ?: ""
        }

        /**
         * Clear the store.
         * <p>
         *     <pre><code> val isCleared = StringsLoader.clear() </code></pre>
         * </p>
         *
         * @return True when the store has already been initialized and has been successfully cleared, false otherwise
         */
        fun clear(): Boolean {
            checkMainThread()

            return loader.clear<Any, String>()
        }
    }

}