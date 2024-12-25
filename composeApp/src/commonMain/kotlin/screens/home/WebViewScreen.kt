package screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import models.NavigationItem
import navigation.bottomNavigation.NavigationDrawer
import screens.loading.LoadingIndicatorScreen

@Composable
fun WebViewScreen(
    webViewUrl: String,
    progressColor: Long,
    onNavigate: (rootName: String) -> Unit,
    subCategories: List<NavigationItem>,
    drawerState: DrawerState,
    backgroundColor: Long,
    textIconActiveColor: Long,
    textIconInactiveColor: Long
) {
    val webViewState = rememberWebViewState(webViewUrl)
    val isLoading by remember(webViewState) { mutableStateOf(webViewState.isLoading) }

    if (isLoading) LoadingIndicatorScreen(progressColor)
    else NavigationDrawer(
        isLeftSide = true,
        screenContent = {
            WebView(
                modifier = Modifier.fillMaxSize(),
                state = webViewState
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
}
