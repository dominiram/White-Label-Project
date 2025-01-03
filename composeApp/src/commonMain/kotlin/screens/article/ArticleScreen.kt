package screens.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import screens.loading.LoadingIndicatorScreen

@Composable
fun ArticleScreen(articleUrl: String, progressColor: Long) {
    val webViewState = rememberWebViewState(articleUrl)
    val isLoading = webViewState.isLoading

    if (isLoading) LoadingIndicatorScreen(progressColor)
    WebView(modifier = Modifier.fillMaxSize(), state = webViewState)
}
