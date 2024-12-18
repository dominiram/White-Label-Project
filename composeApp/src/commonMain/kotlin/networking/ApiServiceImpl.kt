package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLQueryComponent
import kotlinx.coroutines.flow.Flow
import models.MainConfig
import models.MainConfigWrapper
import org.lighthousegames.logging.logging
import whitelabelproject.buildKonfig.BuildKonfig

class ApiServiceImpl(private val httpClient: HttpClient) : ApiService {
    private val log = logging("ApiService")

    override suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>> {
        val apiKey = "%Q/JE31Su;H%Z*8.KuHY"
        val encodedApiKey = apiKey.encodeURLQueryComponent()

        return toNetworkResultFlow {
            val result = httpClient.get(BuildKonfig.BASE_URL + CONFIG_URL_PART + encodedApiKey)
                .body<MainConfigWrapper>().result

            NetworkResult.Success(result)
        }
    }

    companion object {
        private const val CONFIG_URL_PART = "/api/v1/android/get-settings?api_key="
    }
}
