package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.settings.SettingsRepo
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CollectionsViewModel(private val settingsRepo: SettingsRepo) : ViewModel() {
    val session = settingsRepo.session

    @OptIn(DelicateCoroutinesApi::class)
    fun signOut() {
        GlobalScope.launch {
            settingsRepo.updateSession {
                it.toBuilder().clear().build()
            }
        }
    }
}
