package di

import config.MainViewModel
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
    single<ApiService> { ApiServiceImpl() }
    single<MainRepository> { MainRepositoryImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}
