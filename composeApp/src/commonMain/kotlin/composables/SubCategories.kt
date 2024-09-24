package composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubCategory(title: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = title,
        style = TextStyle(
            fontSize = 24.sp,
            color = Color.White
        )
    )
}
