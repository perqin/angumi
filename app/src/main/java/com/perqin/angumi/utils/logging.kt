package com.perqin.angumi.utils

import android.util.Log

fun logI(tag: String, msg: String) = Log.i(tag, msg)
fun logE(tag: String, msg: String, throwable: Throwable? = null) = Log.e(tag, msg, throwable)
