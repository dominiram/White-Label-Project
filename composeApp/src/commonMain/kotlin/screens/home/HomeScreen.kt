package screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import models.NavigationItem
import navigation.bottomNavigation.NavigationDrawer
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun HomeScreen(
    webViewUrl: String,
    onNavigate: (rootName: String) -> Unit,
    subCategories: List<NavigationItem>,
    drawerState: DrawerState
) {
    Surface {
        val webViewState = rememberWebViewState(webViewUrl)
        val viewModel = koinViewModel<HomeViewModel>()

        NavigationDrawer(
            isLeftSide = true,
            screenContent = {
                WebView(
                    modifier = Modifier.fillMaxSize(),
                    state = webViewState
                )
            },
            onNavigationItemClicked = onNavigate,
            drawerState = drawerState,
            navigationItems = subCategories
        )
    }
}
