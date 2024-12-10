package networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NetworkResult<out T>(
    val status: ApiStatus,
    val success: Boolean,
    @SerialName("result") val data: T?,
    val message: String?
) {

    data class Success<out T>(val t: T?) :
        NetworkResult<T>(status = ApiStatus.SUCCESS, data = t, success = true, message = null)

    data class Error<out T>(val t: T?, val exception: String) :
        NetworkResult<T>(status = ApiStatus.ERROR, data = t, success = false, message = exception)

    data class Loading<out T>(val isLoading: Boolean) :
        NetworkResult<T>(status = ApiStatus.LOADING, data = null, success = false, message = null)
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING,
}
