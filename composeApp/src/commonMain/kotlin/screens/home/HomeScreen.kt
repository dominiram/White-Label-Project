package screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(webViewUrl: String, onNavigate: (rootName: String) -> Unit) {

    val viewModel = koinViewModel<HomeViewModel>()
    val webViewState = rememberWebViewState(webViewUrl)

    WebView(state = webViewState)
}
