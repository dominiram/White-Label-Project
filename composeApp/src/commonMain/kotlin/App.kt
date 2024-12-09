import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.Main
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pushNotifications.initPushNotifications

@Composable
@Preview
fun App() {

    initKoin()
    initPushNotifications()

    val viewModel = koinViewModel<AppViewModel>()
    viewModel.subscribeToNewsChannel()

    MaterialTheme {
        viewModel.initPushNotificationToken()
        Main()
    }
}
