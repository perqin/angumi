package com.perqin.angumi

import android.app.Application
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.data.user.UserRepo
import com.perqin.angumi.di.appModule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AngumiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AngumiApplication)
            modules(appModule)
        }

        setupSession()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupSession() {
        // TODO: Maybe need runBlocking?
        GlobalScope.launch {
            val settingsRepo = get<SettingsRepo>()
            val userRepo = get<UserRepo>()
            val session = settingsRepo.session.first()
            if (session.isSignedIn()) {
                userRepo.setMeId(session.userId)
            }
        }
    }
}
