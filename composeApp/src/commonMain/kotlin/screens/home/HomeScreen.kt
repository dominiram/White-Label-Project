package screens.home

import androidx.compose.runtime.Composable
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun HomeScreen(webViewUrl: String, onNavigate: (rootName: String) -> Unit) {

    val webViewState = rememberWebViewState(webViewUrl)

    WebView(state = webViewState)
}
