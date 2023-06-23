package com.perqin.angumi.data.settings

import android.content.Context

class SettingsRepo(context: Context) {
    private val flagsStore = context.flagsDataStore

    val flags get() = flagsStore.data
}
