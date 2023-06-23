package com.perqin.angumi

import android.app.Application
import com.perqin.angumi.di.appModule
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
    }
}
