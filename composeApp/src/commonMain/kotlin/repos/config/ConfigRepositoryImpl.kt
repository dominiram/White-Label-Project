package repos.config

import models.MainConfig
import networking.ApiService

class ConfigRepositoryImpl(private val apiService: ApiService) : ConfigRepository {

    private var config: MainConfig? = null

    override suspend fun getAppConfig() = apiService.getAppConfig()

    override fun saveApplicationConfig(config: MainConfig) {
        this.config = config
    }

    override fun getBottomMenu() = config?.languageList?.find { it.isDefault }?.bottomMenu
}
