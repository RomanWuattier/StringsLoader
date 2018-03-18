package com.romanwuattier.stringsloader.utils

import android.os.Looper

fun checkMainThread() {
    if (Thread.currentThread() != Looper.getMainLooper().thread) {
        throw IllegalStateException("Execution was not running on the main thread.")
    }
}