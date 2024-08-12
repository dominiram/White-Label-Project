package repos

import networking.ApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//TODO: look into ApiServiceImpl, since it causes the issue
class MainRepositoryImpl : MainRepository, KoinComponent {

    private val apiService: ApiService by inject()

    override suspend fun getAppConfig() = apiService.getAppConfig()
}
