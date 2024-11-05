package repos.config

import models.MainConfig
import kotlinx.coroutines.flow.Flow
import networking.NetworkResult

interface ConfigRepository {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>>
}
