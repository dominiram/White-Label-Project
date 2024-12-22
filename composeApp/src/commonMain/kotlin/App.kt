import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import config.Main
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import di.initKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
        requestRuntimePermissions()
    }
}

@Composable
private fun requestRuntimePermissions() {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()

    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    coroutineScope.launch { controller.providePermission(Permission.REMOTE_NOTIFICATION) }
}
