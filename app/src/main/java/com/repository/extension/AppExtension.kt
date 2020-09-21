package com.repository.extension

import android.util.Log.*
import com.repository.BuildConfig

fun Any.debugLog(message: String) {
    if (BuildConfig.DEBUG)
        d(javaClass.simpleName, message)
}

fun Any.infoLog(message: String) {
    if (BuildConfig.DEBUG)
        i(javaClass.simpleName, message)
}

fun Any.errorLog(message: String) {
    if (BuildConfig.DEBUG)
        e(javaClass.simpleName, message)
}