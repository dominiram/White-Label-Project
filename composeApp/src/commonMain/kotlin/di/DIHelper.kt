package di

import config.MainViewModel
import networking.ApiService
import networking.ApiServiceImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module
import repos.MainRepository
import repos.MainRepositoryImpl

fun initKoin() = startKoin {
    modules(modules)
}

val modules = module {
    single<ApiService> { ApiServiceImpl() }
    single<MainRepository> { MainRepositoryImpl(get()) }
    factory { MainViewModel(get()) }
}
