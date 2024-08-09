import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.initKoin
import navigation.bottomNavigation.HomeNav
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        HomeNav()
    }
}
