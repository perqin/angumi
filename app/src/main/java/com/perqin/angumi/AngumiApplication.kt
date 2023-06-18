package com.perqin.angumi

import android.app.Application
import com.perqin.angumi.data.settings.SettingsRepo

class AngumiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SettingsRepo.setup(this)
    }
}
