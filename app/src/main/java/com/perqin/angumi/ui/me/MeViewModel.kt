package com.perqin.angumi.ui.me

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.domains.user.UserRepo
import com.perqin.angumi.data.settings.SettingsRepo
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MeViewModel(private val settingsRepo: SettingsRepo, private val userRepo: UserRepo) :
    ViewModel() {
    val user = userRepo.me

    suspend fun reload() {
        userRepo.reloadMe()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun signOut() {
        GlobalScope.launch {
            settingsRepo.updateSession {
                it.toBuilder().clear().build()
            }
        }
    }
}
