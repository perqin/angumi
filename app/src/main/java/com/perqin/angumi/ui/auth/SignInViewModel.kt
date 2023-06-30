package com.perqin.angumi.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perqin.angumi.data.auth.OAuthService
import com.perqin.angumi.data.settings.SettingsRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignInViewModel(
    private val settingsRepo: SettingsRepo,
    private val oAuthService: OAuthService
) : ViewModel() {
    val oAuthState = oAuthService.state

    fun notNewToSignIn() {
        viewModelScope.launch {
            if (!settingsRepo.flags.first().signInLater) {
                settingsRepo.updateFlags {
                    it.toBuilder().setSignInLater(true).build()
                }
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            oAuthService.onReset()
        }
    }
}
