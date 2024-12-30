package config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.MainConfig
import networking.ApiStatus
import repos.config.ConfigRepository

class MainViewModel(private val configRepository: ConfigRepository) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())

    val homeViewState = _homeState.asStateFlow()

    init {
        getConfig()
    }

    private fun getConfig() = viewModelScope.launch {
        try {
            configRepository.getAppConfig().collect { response ->
                when (response.status) {
                    ApiStatus.LOADING -> {
                        onHomeStateLoading()
                        delay(SPLASH_SCREEN_ADDITIONAL_DURATION_MS)
                    }

                    ApiStatus.SUCCESS -> onHomeStateSuccess(response.data)
                    ApiStatus.ERROR -> onHomeStateError(response.message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onHomeStateError("Failed to fetch data")
        }
    }

    private fun onHomeStateSuccess(data: MainConfig?) = _homeState.update {
        it.copy(
            isLoading = false,
            errorMessage = "",
            responseData = data
        )
    }.also { data?.let { config -> configRepository.saveApplicationConfig(config) } }

    private fun onHomeStateError(errorMessage: String?) = _homeState.update {
        it.copy(
            isLoading = false,
            errorMessage = errorMessage,
            responseData = null
        )
    }

    private fun onHomeStateLoading() = _homeState.update { it.copy(isLoading = true) }

    companion object {
        private const val SPLASH_SCREEN_ADDITIONAL_DURATION_MS = 1500L
    }
}
