package repos

import networking.ApiService

class MainRepositoryImpl(private val apiService: ApiService) : MainRepository {

    override suspend fun getAppConfig() = apiService.getAppConfig()
}
