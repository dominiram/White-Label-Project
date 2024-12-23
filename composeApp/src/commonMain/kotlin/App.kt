import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import config.Main
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
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
        requestRuntimePermissions(Permission.REMOTE_NOTIFICATION)
    }
}

@Composable
private fun requestRuntimePermissions(permission: Permission) {
    runCatching {
        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()

        val controller: PermissionsController =
            remember(factory) { factory.createPermissionsController() }

        BindEffect(controller)
        val coroutineScope: CoroutineScope = rememberCoroutineScope()

        coroutineScope.launch { grantPermissionIfNeeded(permission, controller) }
    }
}

suspend fun grantPermissionIfNeeded(permission: Permission, controller: PermissionsController) {
    if (!controller.isPermissionGranted(permission)) {
        try {
            controller.providePermission(permission)
        } catch (e: DeniedException) {
            controller.openAppSettings()
        }
        catch (e: RequestCanceledException) {
            controller.openAppSettings()
        } catch (e: Exception) { e.printStackTrace() }
    }
}
