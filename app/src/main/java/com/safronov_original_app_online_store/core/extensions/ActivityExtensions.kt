package com.safronov_original_app_online_store.core.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager

fun Activity.logE(msg: String) {
    Log.e(TAG, msg)
}

fun Activity.logD(msg: String) {
    Log.d(TAG, msg)
}

fun Activity.showKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}