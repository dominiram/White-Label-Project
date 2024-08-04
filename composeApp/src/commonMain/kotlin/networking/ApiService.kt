package networking

import models.MainConfig
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>>
}
