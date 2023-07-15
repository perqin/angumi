package com.perqin.angumi.ui.me

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MeViewModel(private val settingsRepo: SettingsRepo, private val userRepo: UserRepo) :
    ViewModel() {
    val user = userRepo.me

    suspend fun reload() {
        userRepo.ensureMe()
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
