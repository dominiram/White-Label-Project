package repos

import models.MainConfig
import kotlinx.coroutines.flow.Flow
import networking.NetworkResult

interface MainRepository {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>>
}
