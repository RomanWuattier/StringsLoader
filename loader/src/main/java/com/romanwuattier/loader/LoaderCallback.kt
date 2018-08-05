package com.romanwuattier.loader

/**
 * Callback called to notify that the asynchronous loading job has completed.
 */
interface LoaderCallback {

    fun onComplete()

    fun onError(throwable: Throwable)
}
