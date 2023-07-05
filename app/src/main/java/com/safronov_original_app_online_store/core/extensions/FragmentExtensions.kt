package com.safronov_original_app_online_store.core.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

const val TAG = "iLog"

fun Fragment.logE(msg: String) {
    Log.e(TAG, msg)
}

fun Fragment.logD(msg: String) {
    Log.d(TAG, msg)
}

fun Fragment.toastS(msg: String) {
    if (context != null) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showInputMethod(view: View) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(view, 0)
}