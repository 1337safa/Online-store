package com.safronov_original_app_online_store.core.extensions

import android.util.Log

fun Any.thisClassName(): String {
    return this.javaClass.name
}

fun Any.logE(msg: String) {
    Log.e(TAG, msg)
}

fun Any.logD(msg: String) {
    Log.d(TAG, msg)
}