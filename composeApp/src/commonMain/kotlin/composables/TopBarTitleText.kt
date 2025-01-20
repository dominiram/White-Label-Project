package composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TopBarTitleText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(400)
        )
    )
}
