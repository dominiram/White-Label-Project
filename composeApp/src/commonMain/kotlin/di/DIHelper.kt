package di

import config.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import networking.ApiService
import networking.ApiServiceImpl
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import repos.MainRepository
import repos.MainRepositoryImpl
import screens.home.HomeViewModel

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
                url(Constants.BASE_URL)
            }
        }
    }

    single<ApiService> { ApiServiceImpl(get()) }
    single<MainRepository> { MainRepositoryImpl(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

object Constants {
    const val BASE_URL = "https://ktor.io/docs/"
}
