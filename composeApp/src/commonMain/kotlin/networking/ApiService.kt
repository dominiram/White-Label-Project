package networking

import apiModels.MainConfig
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getAppConfig(): Flow<NetWorkResult<MainConfig>>
}
