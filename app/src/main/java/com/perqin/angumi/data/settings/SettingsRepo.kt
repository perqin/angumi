package com.perqin.angumi.data.settings

import android.content.Context
import androidx.datastore.core.DataStore

// TODO: Refactor with Koin
object SettingsRepo {
    private lateinit var flagsStore: DataStore<Flags>

    val flags get() = flagsStore.data

    fun setup(context: Context) {
        flagsStore = context.flagsDataStore
    }
}
