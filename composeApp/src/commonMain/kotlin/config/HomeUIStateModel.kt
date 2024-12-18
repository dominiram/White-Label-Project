package config

import models.MainConfig


sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Error(val errorMessage: String) : HomeScreenState()
    data class Success(val responseData: MainConfig) : HomeScreenState()
}

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val responseData: MainConfig? = null
) {
    fun toUiState(): HomeScreenState = when {
        isLoading -> HomeScreenState.Loading
        responseData != null -> HomeScreenState.Success(responseData)
        else -> HomeScreenState.Error(errorMessage ?: BASIC_ERROR)
    }
}

private const val BASIC_ERROR = "Error"
