package com.perqin.angumi.data.settings

import android.content.Context

class SettingsRepo(context: Context) {
    private val flagsStore = context.flagsDataStore
    private val sessionStore = context.sessionDataStore

    val flags get() = flagsStore.data
    val session get() = sessionStore.data

    suspend fun updateFlags(transform: suspend (t: Flags) -> Flags) {
        flagsStore.updateData(transform)
    }

    suspend fun updateSession(transform: suspend (t: Session) -> Session) {
        sessionStore.updateData(transform)
    }
}
