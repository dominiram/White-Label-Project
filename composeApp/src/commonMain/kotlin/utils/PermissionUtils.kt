package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun requestRuntimePermissions(permission: Permission) {
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
