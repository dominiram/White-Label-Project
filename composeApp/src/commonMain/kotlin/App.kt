import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.initKoin
import navigation.bottomNavigation.MainBottomNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import pushNotifications.initPushNotifications

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {

    initKoin()
    initPushNotifications()

    val viewModel = koinViewModel<AppViewModel>()

    MaterialTheme {
        viewModel.initPushNotificationToken()
        MainBottomNavigation()
    }
}
