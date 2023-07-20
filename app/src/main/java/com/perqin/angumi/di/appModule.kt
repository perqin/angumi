package com.perqin.angumi.di

import android.content.Context
import androidx.room.Room
import com.perqin.angumi.data.api.angumi.AngumiClient
import com.perqin.angumi.data.api.angumi.AuthApi
import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.api.bangumi.CollectionApi
import com.perqin.angumi.data.api.bangumi.UserApi
import com.perqin.angumi.data.auth.OAuthService
import com.perqin.angumi.data.collection.CollectionLocalSource
import com.perqin.angumi.data.collection.CollectionRemoteSource
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.room.CacheDatabase
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.data.user.UserLocalSource
import com.perqin.angumi.data.user.UserRemoteSource
import com.perqin.angumi.data.user.UserRepo
import com.perqin.angumi.ui.auth.SignInViewModel
import com.perqin.angumi.ui.collections.CollectionsPageViewModel
import com.perqin.angumi.ui.collections.CollectionsViewModel
import com.perqin.angumi.ui.me.MeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
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
    ignoreUnknownKeys = true
}

val appModule = module {
    single {
        val context = get<Context>()
        Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            (context.externalCacheDir ?: context.cacheDir).resolve("app_cache.db").absolutePath
        ).build()
    }
    single { get<CacheDatabase>().userDao }
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
        val settingsRepo: SettingsRepo = get()
        val angumiClient: AngumiClient = get()
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(bangumiClientJson)
            }
            install(Auth) {
                bearer {
                    // TODO: Maybe need some separate class to provide loadTokens and refreshTokens.
                    //  Note that these methods should not be in OAuthService, otherwise will result
                    //  in cycling dependencies.
                    loadTokens {
                        settingsRepo.session.first()
                            .takeIf { it.isSignedIn() }
                            ?.let { BearerTokens(it.accessToken, it.refreshToken) }
                    }
                    refreshTokens {
                        val res =
                            angumiClient.auth.refreshTokens(settingsRepo.session.first().refreshToken)
                        settingsRepo.updateSession {
                            it.toBuilder()
                                .setAccessToken(res.accessToken)
                                .setRefreshToken(res.refreshToken)
                                .setExpiresAfter(System.currentTimeMillis() + res.expiresIn)
                                .build()
                        }
                        BearerTokens(res.accessToken, res.refreshToken)
                    }
                }
            }
        }
    }
    single { UserApi(get(named(HttpClientQualifier.BANGUMI))) }
    single { CollectionApi(get(named(HttpClientQualifier.BANGUMI))) }
    single { BangumiClient(get(), get()) }
    single { OAuthService(get(), get(), get()) }
    single { SettingsRepo(get()) }
    single { UserLocalSource(get()) }
    single { UserRemoteSource(get()) }
    single { UserRepo(get(), get()) }
    single { CollectionLocalSource() }
    single { CollectionRemoteSource(get()) }
    single { CollectionRepo(get(), get()) }

    viewModel { SignInViewModel(get(), get()) }
    viewModel { MeViewModel(get(), get()) }
    viewModel { CollectionsViewModel(get()) }
    viewModel { params -> CollectionsPageViewModel(params.get(), get(), get(), get()) }
}
