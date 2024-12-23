import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.Main
import dev.icerock.moko.permissions.Permission
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pushNotifications.initPushNotifications
import utils.requestRuntimePermissions

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
        requestRuntimePermissions(Permission.REMOTE_NOTIFICATION)
    }
}
