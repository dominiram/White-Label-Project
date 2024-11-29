package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import models.MainConfig
import whitelabelproject.buildKonfig.BuildKonfig

class ApiServiceImpl(private val httpClient: HttpClient) : ApiService {

    override suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>> =
        toResultFlow {
            httpClient.get(BuildKonfig.BASE_URL + CONFIG_URL_PART).body<NetworkResult<MainConfig>>()
        }

    companion object {
        private const val CONFIG_URL_PART = "/api/v1/android/get-settings?api_key=%Q/JE31Su;H%Z*8.KuHY"
    }
}
