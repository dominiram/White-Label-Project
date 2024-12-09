package repos.config

import models.MainConfig
import kotlinx.coroutines.flow.Flow
import models.MainNavigationItem
import networking.NetworkResult

interface ConfigRepository {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig?>>
    fun saveApplicationConfig(config: MainConfig)
    fun getBottomMenu(): List<MainNavigationItem>?
}
