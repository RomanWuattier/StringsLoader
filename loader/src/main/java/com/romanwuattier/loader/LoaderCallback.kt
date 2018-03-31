package com.romanwuattier.loader

/**
 * Callback called to notify that the loading job is completed.
 */
interface LoaderCallback {

    fun onComplete()

    fun onError()
}