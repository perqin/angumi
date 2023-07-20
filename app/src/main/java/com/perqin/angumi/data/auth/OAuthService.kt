package com.perqin.angumi.data.auth

import android.net.Uri
import android.util.Log
import com.perqin.angumi.data.api.angumi.AngumiClient
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OAuthService(
    private val settingsRepo: SettingsRepo,
    private val userRepo: UserRepo,
    private val angumiClient: AngumiClient,
) {
    companion object {
        private const val TAG = "OAuthService"
    }

    private val _state = MutableStateFlow(OAuthState.NONE)
    val state: StateFlow<OAuthState> = _state

    suspend fun onStarted() {
        _state.emit(OAuthState.INITIATED)
    }

    suspend fun handleRedirect(uri: Uri) {
        _state.emit(OAuthState.HANDLING_CALLBACK)
        val code = uri.getQueryParameter("code").orEmpty()
        try {
            val res = angumiClient.auth.getAccessToken(code)
            Log.d(TAG, "handleRedirect: Succeeded, userId is ${res.userId}")
            settingsRepo.updateSession {
                it.toBuilder()
                    .setUserId(res.userId!!)
                    .setAccessToken(res.accessToken)
                    .setRefreshToken(res.refreshToken)
                    .setExpiresAfter(System.currentTimeMillis() + res.expiresIn)
                    .build()
            }
            // Also pre-load me
            userRepo.setMeId(res.userId!!)
            userRepo.reloadMe()
            _state.emit(OAuthState.SUCCESSFUL)
        } catch (e: Exception) {
            Log.e(TAG, "handleRedirect: Failed", e)
            _state.emit(OAuthState.FAILED)
        }
    }

    suspend fun onReset() {
        _state.emit(OAuthState.NONE)
    }

    suspend fun onFailed() {
        _state.emit(OAuthState.FAILED)
    }
}
