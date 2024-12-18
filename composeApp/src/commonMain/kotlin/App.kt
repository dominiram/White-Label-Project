import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.Main
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pushNotifications.initPushNotifications

var isKoinInitialized = false

@Composable
@Preview
fun App() {

    if (!isKoinInitialized) {
        initKoin()
        isKoinInitialized = true
    }

    initPushNotifications()

    val viewModel = koinViewModel<AppViewModel>()
    viewModel.subscribeToNewsChannel()

    MaterialTheme {
        viewModel.initPushNotificationToken()
        Main()
    }
}
