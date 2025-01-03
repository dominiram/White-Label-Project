package di

import AppViewModel
import config.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import navigation.bottomNavigation.BottomNavigationViewModel
import networking.ApiService
import networking.ApiServiceImpl
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import repos.config.ConfigRepository
import repos.config.ConfigRepositoryImpl
import repos.pushNotifications.PushNotificationsRepository
import repos.pushNotifications.PushNotificationsRepositoryImpl
import screens.home.HomeViewModel
import whitelabelproject.buildKonfig.BuildKonfig

fun initKoin() = startKoin {
    modules(modules)
}

val modules = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                }, contentType = ContentType.Any)
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            defaultRequest {
                url(BuildKonfig.BASE_URL)
            }
        }
    }

    single<ApiService> { ApiServiceImpl(get()) }
    single<PushNotificationsRepository> { PushNotificationsRepositoryImpl() }
    single<ConfigRepository> { ConfigRepositoryImpl(get()) }
    viewModel { AppViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { BottomNavigationViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
}
