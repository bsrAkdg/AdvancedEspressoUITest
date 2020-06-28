package com.bsrakdg.blogpost.util

import android.util.Log
import com.bsrakdg.blogpost.util.Constants.DEBUG
import com.bsrakdg.blogpost.util.Constants.TAG

fun printLogD(className: String?, message: String ) {
    if (DEBUG) {
        Log.d(TAG, "$className: $message")
    }
}