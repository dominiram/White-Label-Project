import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.initKoin
import navigation.bottomNavigation.MainBottomNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import pushNotifications.initPushNotifications

@Composable
@Preview
fun App() {
    initKoin()
    initPushNotifications()

    MaterialTheme {
        MainBottomNavigation()
    }
}
