package com.safronov_original_app_online_store.core.extensions

import android.util.Log
import androidx.fragment.app.Fragment

const val TAG = "iLog"

fun Fragment.logE(msg: String) {
    Log.e(TAG, msg)
}