package networking

import models.MainConfig
import kotlinx.coroutines.flow.Flow
import models.MainConfigWrapper

interface ApiService {
    suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>>
}
