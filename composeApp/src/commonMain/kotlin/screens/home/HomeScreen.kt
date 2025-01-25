package screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import models.NavigationItem
import navigation.bottomNavigation.NavigationDrawer
import screens.loading.LoadingIndicatorScreen

@Composable
fun HomeScreen(
    webViewUrl: String,
    isLeftSideDrawer: Boolean,
    progressColor: Long,
    onNavigate: (rootName: String) -> Unit,
    subCategories: List<NavigationItem>,
    drawerState: DrawerState,
    backgroundColor: Long,
    textIconActiveColor: Long,
    textIconInactiveColor: Long,
    navigator: WebViewNavigator,
    isArticleOpened: (Boolean) -> Unit
) {
    Surface {
        val webViewState = rememberWebViewState(webViewUrl)
        val isLoading = webViewState.isLoading

        key(webViewState) {
            isArticleOpened(webViewState.lastLoadedUrl?.contains("/get-article/") == true)
        }

        NavigationDrawer(
            isLeftSide = isLeftSideDrawer,
            screenContent = {
                WebView(
                    modifier = Modifier.fillMaxSize(),
                    state = webViewState,
                    navigator = navigator
                )
            },
            onNavigationItemClicked = onNavigate,
            isSubcategorySelected = { subcategoryUrl -> webViewUrl == subcategoryUrl },
            drawerState = drawerState,
            navigationItems = subCategories,
            backgroundColor = backgroundColor,
            textIconActiveColor = textIconActiveColor,
            textIconInactiveColor = textIconInactiveColor
        )

        if (isLoading) LoadingIndicatorScreen(progressColor)
    }
}
