package navigation.bottomNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Surface
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.MainNavigationItem
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(
    backgroundColor: Long,
    textIconActiveColor: Long,
    textIconInactiveColor: Long,
    mainNavigationItems: List<MainNavigationItem> = arrayListOf(),
    shouldShowBottomAppBar: Boolean,
    navigateBottomBar: (String) -> Unit,
    isItemSelected: (MainNavigationItem) -> Boolean,
    closeNavigationDrawer: () -> Unit
) {
    AppBottomNavigationBar(
        shouldShowBottomAppBar = shouldShowBottomAppBar,
        backgroundColor = backgroundColor,
        content = {
            mainNavigationItems.forEach { item ->
                if (listOf(item.route, item.name).all { it != null })
                    AppBottomNavigationBarItem(
                        icon = item.tabIcon,
                        label = item.name!!,
                        textIconActiveColor = textIconActiveColor,
                        textIconInactiveColor = textIconInactiveColor,
                        onBottomNavItemClick = {
                            navigateBottomBar(item.getFirstSubcategoryRoute())
                            closeNavigationDrawer()
                        },
                        isSelected = isItemSelected(item)
                    )
            }
        }
    )
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    shouldShowBottomAppBar: Boolean,
    backgroundColor: Long,
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = Color(backgroundColor),
        contentColor = Color(backgroundColor),
        modifier = modifier.background(color = Color(backgroundColor)).windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        if (shouldShowBottomAppBar) {
            Column {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Composable
fun RowScope.AppBottomNavigationBarItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    textIconActiveColor: Long,
    textIconInactiveColor: Long,
    label: String,
    onBottomNavItemClick: () -> Unit,
    isSelected: Boolean,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .clickable(onClick = onBottomNavItemClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = icon.toString(),
            contentScale = ContentScale.Crop,
            colorFilter = if (isSelected) ColorFilter.tint(Color(textIconActiveColor)) else ColorFilter.tint(
                Color(textIconInactiveColor)
            ),
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            style = TextStyle(
                fontWeight = if (isSelected) FontWeight(700) else FontWeight(400),
                color = if (isSelected) Color(textIconActiveColor) else Color(textIconInactiveColor)
            )
        )
    }
}
