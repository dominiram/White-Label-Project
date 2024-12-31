package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLQueryComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import models.MainConfig
import models.MainConfigWrapper
import org.lighthousegames.logging.logging
import whitelabelproject.buildKonfig.BuildKonfig
import kotlin.coroutines.CoroutineContext

class ApiServiceImpl(
    private val httpClient: HttpClient,
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : ApiService {
    private val log = logging("ApiService")

    override suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>> =
        withContext(ioCoroutineContext) {
            val apiKey = BuildKonfig.API_KEY
            val encodedApiKey = apiKey.encodeURLQueryComponent()

            return@withContext toNetworkResultFlow {
                val result = httpClient.get(BuildKonfig.BASE_URL + CONFIG_URL_PART + encodedApiKey)
                    .body<MainConfigWrapper>().result

                NetworkResult.Success(result)
            }
        }

    companion object {
        private const val CONFIG_URL_PART = "/api/v1/android/get-settings?api_key="
    }
}
