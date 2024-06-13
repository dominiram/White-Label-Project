import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import config.Main
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        Main()
    }
}
