package com.perqin.angumi.di

import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.ui.auth.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SettingsRepo(get()) }

    viewModel { SignInViewModel(get()) }
}
