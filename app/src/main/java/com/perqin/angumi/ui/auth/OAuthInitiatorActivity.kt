package com.perqin.angumi.ui.auth

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.perqin.angumi.BuildConfig
import com.perqin.angumi.data.auth.OAuthService
import com.perqin.angumi.data.auth.OAuthState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * This transparent activity launches the Custom Tabs activity for OAuth flow and wait for response.
 * Why this activity:
 * The Custom Tabs activity cannot be closed explicitly, so we have to bring the user back to the
 * UI initiating the auth flow after receiving callback.
 */
class OAuthInitiatorActivity : AppCompatActivity() {
    private val oAuthService: OAuthService by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        // Once resumed from the finishing Custom Tabs activity, we also finish here and show result
        // in the previous UI that initiates the auth flow
        GlobalScope.launch {
            when (oAuthService.state.first()) {
                OAuthState.NONE -> {
                    oAuthService.onStarted()
                    val uri = Uri.parse("https://bgm.tv/oauth/authorize")
                        .buildUpon()
                        .appendQueryParameter("client_id", BuildConfig.BANGUMI_CLIENT_ID)
                        .appendQueryParameter("response_type", "code")
                        .appendQueryParameter(
                            "redirect_uri",
                            "https://angumi.perqin.cn/oauth-callback"
                        )
                        .build()
                    CustomTabsIntent.Builder().build().launchUrl(this@OAuthInitiatorActivity, uri)
                }

                // User cancel
                OAuthState.INITIATED -> {
                    oAuthService.onFailed()
                    finish()
                }

                // Simply finish, OAuthService and initiating UI will handle the rest
                else -> {
                    finish()
                }
            }
        }
    }
}
