package com.perqin.angumi.di

import com.perqin.angumi.data.api.angumi.AngumiClient
import com.perqin.angumi.data.api.angumi.AuthApi
import com.perqin.angumi.data.auth.OAuthService
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.ui.auth.SignInViewModel
import com.perqin.angumi.ui.collections.CollectionsViewModel
import com.perqin.angumi.ui.me.MeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private enum class HttpClientQualifier {
    ANGUMI,
}

@OptIn(ExperimentalSerializationApi::class)
private val angumiClientJson = Json {
    namingStrategy = JsonNamingStrategy.SnakeCase
}

val appModule = module {
    single(named(HttpClientQualifier.ANGUMI)) {
        HttpClient {
            install(ContentNegotiation) {
                json(angumiClientJson)
            }
        }
    }
    single { AuthApi(get(named(HttpClientQualifier.ANGUMI))) }
    single { AngumiClient(get()) }
    single { OAuthService(get(), get()) }
    single { SettingsRepo(get()) }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { MeViewModel() }
    viewModel { CollectionsViewModel(get()) }
}
