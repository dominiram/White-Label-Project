package screens.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MainConfigErrorScreen() {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Error",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight(700))
        )
    }
}