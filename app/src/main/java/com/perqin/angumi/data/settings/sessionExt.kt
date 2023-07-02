package com.perqin.angumi.data.settings

fun Session.isSignedIn() = userId > 0
