package com.perqin.angumi.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.perqin.angumi.data.auth.OAuthService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * This Activity should be launched on top of the Custom Tabs activity, clear both that Custom Tabs
 * activity and itself to bring the [OAuthInitiatorActivity] to the front.
 */
class OAuthCallbackActivity : AppCompatActivity() {
    private val oAuthService: OAuthService by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.Main.immediate) {
            intent?.data?.let {
                oAuthService.handleRedirect(it)
            }
        }
        startActivity(Intent(this, OAuthInitiatorActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        })
    }
}
