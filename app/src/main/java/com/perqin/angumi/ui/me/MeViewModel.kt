package com.perqin.angumi.ui.me

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.user.User
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MeViewModel(private val settingsRepo: SettingsRepo, private val userRepo: UserRepo) :
    ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    suspend fun reload() {
        _user.emit(userRepo.loadMe())
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
