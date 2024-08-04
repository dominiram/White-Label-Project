package config

import models.MainConfig
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import networking.ApiStatus
import repos.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ScreenModel {

    private val _homeState = MutableStateFlow(HomeState())

    private val _homeViewState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)

    val homeViewState = _homeViewState.asStateFlow()

    suspend fun getProducts() = screenModelScope.launch {
        try {
            mainRepository.getAppConfig().collect { response ->
                when (response.status) {
                    ApiStatus.LOADING -> {
                        _homeState.update { it.copy(isLoading = true) }
                    }

                    ApiStatus.SUCCESS -> {
                        _homeState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "",
                                responseData = response.data
                            )
                        }
                    }

                    ApiStatus.ERROR -> {
                        _homeState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                }
                _homeViewState.value = _homeState.value.toUiState()
            }
        } catch (e: Exception) {
            _homeState.update { it.copy(isLoading = false, errorMessage = "Failed to fetch data") }
        }
    }

    sealed class HomeScreenState {
        data object Loading : HomeScreenState()
        data class Error(val errorMessage: String) : HomeScreenState()
        data class Success(val responseData: MainConfig) : HomeScreenState()
    }

    private data class HomeState(
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

    companion object {
        private const val BASIC_ERROR = "Error"
    }
}
