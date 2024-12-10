package networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> toNetworkResultFlow(call: suspend () -> NetworkResult<T?>) : Flow<NetworkResult<T>> {
    return flow {
        emit(NetworkResult.Loading(true))
        val c = call.invoke()
        c.let { response ->
            try {
                println("success = ${response.success}, NetworkResponse = ${response.data}")
                emit(NetworkResult.Success(response.data))
            } catch (e: Exception) {
                emit(NetworkResult.Error(response.data, e.toString()))
            }
        }
    }
}
