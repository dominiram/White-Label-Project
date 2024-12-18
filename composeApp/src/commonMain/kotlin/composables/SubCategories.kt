package composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubCategory(title: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier.padding(top = 4.dp, start = 12.dp).clickable { onClick() },
        text = title,
        style = TextStyle(
            fontSize = 20.sp,
            color = Color.DarkGray,
            fontFamily = FontFamily.SansSerif
        )
    )
}
