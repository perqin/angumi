package com.perqin.angumi.di

import com.perqin.angumi.data.api.angumi.AngumiClient
import com.perqin.angumi.data.api.angumi.AuthApi
import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.api.bangumi.UserApi
import com.perqin.angumi.data.auth.OAuthService
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.ui.auth.SignInViewModel
import com.perqin.angumi.ui.collections.CollectionsViewModel
import com.perqin.angumi.ui.me.MeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
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
    BANGUMI,
}

@OptIn(ExperimentalSerializationApi::class)
private val angumiClientJson = Json {
    namingStrategy = JsonNamingStrategy.SnakeCase
}

@OptIn(ExperimentalSerializationApi::class)
private val bangumiClientJson = Json {
    namingStrategy = JsonNamingStrategy.SnakeCase
}

val appModule = module {
    single(named(HttpClientQualifier.ANGUMI)) {
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(angumiClientJson)
            }
        }
    }
    single { AuthApi(get(named(HttpClientQualifier.ANGUMI))) }
    single { AngumiClient(get()) }
    single(named(HttpClientQualifier.BANGUMI)) {
        val oAuthService: OAuthService = get()
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(bangumiClientJson)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        oAuthService.loadTokens()
                    }
                    refreshTokens {
                        oAuthService.refreshTokens()
                    }
                }
            }
        }
    }
    single { UserApi(get(named(HttpClientQualifier.BANGUMI))) }
    single { BangumiClient(get()) }
    single { OAuthService(get(), get(), get()) }
    single { SettingsRepo(get()) }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { MeViewModel() }
    viewModel { CollectionsViewModel(get()) }
}
