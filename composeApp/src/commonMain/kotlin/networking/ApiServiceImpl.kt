package networking

import models.MainConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import whitelabelproject.buildKonfig.BuildKonfig

class ApiServiceImpl(private val httpClient: HttpClient) : ApiService {

    override suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>> =
        toResultFlow {
            httpClient.get(BuildKonfig.BASE_URL + "config").body<NetworkResult<MainConfig>>()
        }
}
