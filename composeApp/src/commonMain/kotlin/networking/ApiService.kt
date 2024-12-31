package networking

import kotlinx.coroutines.flow.Flow
import models.MainConfig

interface ApiService {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>>
}
