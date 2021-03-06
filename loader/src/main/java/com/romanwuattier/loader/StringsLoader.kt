package com.romanwuattier.loader

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import com.romanwuattier.loader.internal.AnyLoader
import com.romanwuattier.loader.internal.utils.checkMainThread
import java.io.File

/**
 * A static class that provides all the library methods to retrieve [String] from [Any] key
 */
class StringsLoader {

    companion object {
        private val loader = AnyLoader.provideInstance()

        /**
         * Load asynchronously [Any] keys and [String]s value from an [url] and a [ConverterType] into a local store.
         *
         * <p>
         *     In order to be notified when the loading is successful or erroneous, the caller have to implement
         *     the *callback* interface.
         *     <pre><code>
         *         StringsLoader.loadFromRemote(URL, CACHE_DIR, ConverterType.JSON, object : LoaderCallback {
         *             override fun onComplete() {
         *                 // Loading is successful
         *             }
         *
         *             override fun onError() {
         *                 // An error occurred
         *             }
         *         })
         *     </code></pre>
         * </p>
         *
         * @param url The url hosting the data
         * @param cacheDir The absolute path to the application specific cache directory on the filesystem
         * @param converterType The converter used to deserialize
         * @param callback The interface used to propagate data
         */
        fun loadFromRemote(url: String, cacheDir: File, converterType: ConverterType, callback: LoaderCallback) {
            checkMainThread()
            loader.loadFromRemote<Any, String>(url, cacheDir, converterType, callback)
        }

        /**
         * Load asynchronously [Any] keys and [String]s value from a file [path] and a [ConverterType] into a local store.
         *
         * <p>
         *     In order to be notified when the loading is successful or erroneous, the caller have to implement
         *     the *callback* interface.
         *     <pre><code>
         *         StringsLoader.loadFromLocal(PATH, context, ConverterType.JSON, object : LoaderCallback {
         *             override fun onComplete() {
         *                 // Loading is successful
         *             }
         *
         *             override fun onError() {
         *                 // An error occurred
         *             }
         *         })
         *     </code></pre>
         * </p>
         *
         * @param path The local file path containing the data
         * @param context The [Context] to load the file
         * @param converterType The converter used to deserialize
         * @param callback The interface used to propagate data
         */
        fun loadFromLocal(path: String, context: Context, converterType: ConverterType, callback: LoaderCallback) {
            checkMainThread()
            loader.loadFromLocal<Any, String>(path, context, converterType, callback)
        }

        /**
         * Find the given [key] in the store and retrieve its corresponding [String] value. When the [key] is not
         * found, an empty [String] is returned.
         *
         * <p>
         *     <pre><code> val value = StringsLoader.get("key") </code></pre>
         * </p>
         *
         * @param key The key associated to the [String] value
         *
         * @return An empty [String] when the store doesn't contain the key, the [String] value otherwise
         */
        fun get(key: Any): String = loader.get<Any, String>(key) ?: ""

        /**
         * Find the given [key] in the store and returns a string obtained by substituting the specified arguments
         * [args], using the default locale.
         * When the [key] is not found, an empty [String] is returned.
         *
         * @throws java.util.IllegalFormatException If a format string contains an illegal syntax, a format specifier
         * that is incompatible with the given arguments, insufficient arguments given the format string, or other
         * illegal conditions.
         *
         * @see get
         */
        fun get(key: Any, vararg args: Any): String = get(key).format(*args)

        /**
         * Clear the store.
         *
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

        /**
         * Find the given [key] in the store and set its corresponding [String] to the given [AppCompatTextView].
         * When the [key] is not found, an empty [String] is assigned.
         *
         * <p>
         *     <pre><code>StringsLoader.displayTextInView({aTextView}, {aKey})</code></pre>
         * </p>
         *
         * @param view The [AppCompatTextView] to set
         * @param key The key associated to the [String] value
         */
        fun displayTextInView(view: AppCompatTextView, key: Any) {
            checkMainThread()
            view.text = get(key)
        }

        /**
         * Find the given [key] in the store and set its corresponding [String] to the given [AppCompatButton].
         * When the [key] is not found, an empty [String] is assigned.
         *
         * <p>
         *     <pre><code>StringsLoader.displayTextInView({aButton}, {aKey})</code></pre>
         * </p>
         *
         * @param view The [AppCompatButton] to set
         * @param key The key associated to the [String] value
         */
        fun displayTextInView(view: AppCompatButton, key: Any) {
            checkMainThread()
            view.text = get(key)
        }

        /**
         * Find the given [key] in the store and set its corresponding [String] as hint to the given [AppCompatTextView].
         * When the [key] is not found, an empty [String] is assigned.
         *
         * <p>
         *     <pre><code>StringsLoader.displayHintInView({aTextView}, {aKey})</code></pre>
         * </p>
         *
         * @param view The [AppCompatTextView] to set
         * @param key The key associated to the [String] value
         */
        fun displayHintInView(view: AppCompatTextView, key: Any) {
            checkMainThread()
            view.hint = get(key)
        }

        /**
         * Find the given [key] in the store and set its corresponding [String] as hint to the given [AppCompatButton].
         * When the [key] is not found, an empty [String] is assigned.
         *
         * <p>
         *     <pre><code>StringsLoader.displayHintInView({aButton}, {aKey})</code></pre>
         * </p>
         *
         * @param view The [AppCompatButton] to set
         * @param key The key associated to the [String] value
         */
        fun displayHintInView(view: AppCompatButton, key: Any) {
            checkMainThread()
            view.hint = get(key)
        }
    }
}
