package com.romanwuattier.loader

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.romanwuattier.loader.converter.ConverterStrategy
import com.romanwuattier.loader.data.LoadRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

internal class DownloadTask<K, V>(private val loadRequest: LoadRequest,
    private val okHttpClient: OkHttpClient, private val converter: ConverterStrategy,
    private val onSuccess: (ConcurrentHashMap<K, V>, LoaderCallback) -> Unit,
    private val onError: (Throwable, LoaderCallback) -> Unit) : Runnable {

    override fun run() {
        var response: Response? = null

        try {
            val request = Request.Builder().url(loadRequest.url).build()
            response = okHttpClient.newCall(request).execute()
            val map = converter.convert<K, V>(reader = response?.body()?.charStream())
            onSuccess(map, loadRequest.callback)
        } catch (e: IllegalArgumentException) {
            onError(e, loadRequest.callback)
        } catch (e: JsonIOException) {
            onError(e, loadRequest.callback)
        } catch (e: JsonSyntaxException) {
            onError(e, loadRequest.callback)
        } catch (e: IOException) {
            onError(e, loadRequest.callback)
        } catch (e: IllegalStateException) {
            onError(e, loadRequest.callback)
        } finally {
            response?.close()
        }
    }
}