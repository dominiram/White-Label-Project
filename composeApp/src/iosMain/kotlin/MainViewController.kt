import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import dataStore.createDataStore

fun MainViewController() = ComposeUIViewController {
    App(preferences = remember { createDataStore() })
}