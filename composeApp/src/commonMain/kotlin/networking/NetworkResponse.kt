package networking

sealed class NetworkResult<out T>(val status: ApiStatus, val result: T?, val message: String?) {

    data class Success<out T>(val t: T?) :
        NetworkResult<T>(status = ApiStatus.SUCCESS, result = t, message = null)

    data class Error<out T>(val t: T?, val exception: String) :
        NetworkResult<T>(status = ApiStatus.ERROR, result = t, message = exception)

    data class Loading<out T>(val isLoading: Boolean) :
        NetworkResult<T>(status = ApiStatus.LOADING, result = null, message = null)
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING,
}
