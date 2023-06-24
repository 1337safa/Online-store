package com.safronov_original_app_online_store.core.extensions

import android.app.Activity
import android.util.Log

fun Activity.logE(msg: String) {
    Log.e(TAG, msg)
}

fun Activity.logD(msg: String) {
    Log.d(TAG, msg)
}