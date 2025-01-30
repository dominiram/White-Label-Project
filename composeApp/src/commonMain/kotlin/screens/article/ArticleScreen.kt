package screens.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.koin.compose.viewmodel.koinViewModel
import screens.loading.LoadingIndicatorScreen

@Composable
fun ArticleScreen(articleUrl: String, progressColor: Long) {
    val viewModel = koinViewModel<ArticleViewModel>()
    val webViewState = rememberWebViewState(articleUrl)
    val isLoading = webViewState.isLoading

    WebView(modifier = Modifier.fillMaxSize(), state = webViewState)
    if (isLoading) LoadingIndicatorScreen(progressColor)
    viewModel.clearPushNotification()
}
