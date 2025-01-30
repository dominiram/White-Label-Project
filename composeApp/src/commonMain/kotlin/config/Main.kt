package config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import navigation.bottomNavigation.MainBottomNavigation
import org.koin.compose.viewmodel.koinViewModel
import screens.error.MainConfigErrorScreen
import screens.loading.SplashScreen

@Composable
fun Main() {
    val viewModel = koinViewModel<MainViewModel>()
    val homeViewState by viewModel.homeViewState.collectAsState()

    when (homeViewState.toUiState()) {
        is HomeScreenState.Loading -> SplashScreen()
        is HomeScreenState.Error -> MainConfigErrorScreen()
        is HomeScreenState.Success -> MainBottomNavigation()
    }
}
