package com.perqin.angumi.di

import com.perqin.angumi.data.settings.SettingsRepo
import org.koin.dsl.module

val appModule = module {
    single { SettingsRepo(get()) }
}
