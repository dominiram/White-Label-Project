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

class ApiServiceImpl(private val httpClient: HttpClient) : ApiService {

//    private val httpClient = HttpClient {
//        install(ContentNegotiation) {
//            json(Json {
//                ignoreUnknownKeys = true
//                useAlternativeNames = false
//            })
//        }
//
//        defaultRequest {
//            url(BASE_URL)
//        }
//    }

    override suspend fun getAppConfig(): Flow<NetworkResult<MainConfig>> =
        toResultFlow { httpClient.get(BASE_URL + "config").body<NetworkResult<MainConfig>>() }

    companion object {
        private const val BASE_URL = "https://ktor.io/docs/"
    }
}
