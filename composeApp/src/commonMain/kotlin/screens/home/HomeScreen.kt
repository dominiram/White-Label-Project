package screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import models.NavigationItem
import navigation.bottomNavigation.NavigationDrawer
import org.lighthousegames.logging.logging
import screens.loading.LoadingIndicatorScreen

@Composable
fun HomeScreen(
    webViewUrl: String,
    progressColor: Long,
    onNavigate: (rootName: String) -> Unit,
    subCategories: List<NavigationItem>,
    drawerState: DrawerState
) {
    Surface {
        val webViewState = rememberWebViewState(webViewUrl)
        val isLoading by remember { mutableStateOf(webViewState.isLoading) }
        val log = logging("HomeScreen")

        NavigationDrawer(
            isLeftSide = true,
            screenContent = {
                if (isLoading) LoadingIndicatorScreen(progressColor)
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
