package repos

import networking.ApiServiceImpl

class MainRepositoryImpl(private val apiServiceImpl: ApiServiceImpl) : MainRepository {

    override suspend fun getAppConfig() = apiServiceImpl.getAppConfig()
}
