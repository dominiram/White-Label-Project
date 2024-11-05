package repos.config

import networking.ApiService

class ConfigRepositoryImpl(private val apiService: ApiService) : ConfigRepository {

    override suspend fun getAppConfig() = apiService.getAppConfig()
}
