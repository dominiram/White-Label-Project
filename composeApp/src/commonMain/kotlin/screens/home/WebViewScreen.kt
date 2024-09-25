package screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import models.NavigationItem
import navigation.bottomNavigation.NavigationDrawer

@Composable
fun WebViewScreen(
    webViewUrl: String,
    onNavigate: (rootName: String) -> Unit,
    subCategories: List<NavigationItem>,
    drawerState: DrawerState
) {
    val webViewState = rememberWebViewState(webViewUrl)
    WebView(state = webViewState)

    NavigationDrawer(
        isLeftSide = true,
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize())
        },
        onNavigationItemClicked = onNavigate,
        drawerState = drawerState,
        navigationItems = subCategories
    )
}
