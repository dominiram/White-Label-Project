package navigation.bottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import composables.SubCategory
import models.NavigationItem

@Composable
fun NavigationDrawer(
    isLeftSide: Boolean,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    screenContent: @Composable () -> Unit,
    onNavigationItemClicked: (String) -> Unit,
    isSubcategorySelected: (String?) -> Boolean,
    navigationItems: List<NavigationItem>,
    backgroundColor: Long,
    textIconActiveColor: Long,
    textIconInactiveColor: Long
) {
    CompositionLocalProvider(LocalLayoutDirection provides if (isLeftSide) LayoutDirection.Ltr else LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = Modifier,
            gesturesEnabled = false,
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .fillMaxHeight()
                        .background(color = Color(backgroundColor)),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start
                ) {
                    navigationItems.forEach { subCategory ->
                        if (listOf(
                                subCategory.name,
                                subCategory.parentRoute,
                                subCategory.route
                            ).all { it != null }
                        ) SubCategory(
                            title = subCategory.name!!,
                            isSelected = isSubcategorySelected(subCategory.url),
                            onClick = { onNavigationItemClicked(subCategory.getFullRoute()) },
                            activeColor = textIconActiveColor,
                            inactiveColor = textIconInactiveColor
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = Color.LightGray)
                        )
                    }
                }
            }
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                screenContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    logoUrl: String,
    backgroundColor: Long,
    textIconActiveColor: Long,
    canNavigateBack: Boolean,
    hasGotLeftSubNavigation: Boolean,
    hasGotRightSubNavigation: Boolean,
    onDrawerClicked: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = if (hasGotLeftSubNavigation) Alignment.CenterStart else Alignment.CenterEnd
            ) {

                AsyncImage(
                    modifier = Modifier
                        .align(if (hasGotLeftSubNavigation) Alignment.CenterEnd else Alignment.CenterStart)
                        .fillMaxHeight()
                        .width(100.dp)
                        .padding(horizontal = 8.dp),
                    model = logoUrl,
                    contentDescription = null
                )

                if (hasGotLeftSubNavigation)
                    Icon(
                        modifier = Modifier.clickable { onDrawerClicked() },
                        imageVector = Icons.Default.Menu,
                        tint = Color(textIconActiveColor),
                        contentDescription = null
                    )

                Text(modifier = Modifier.align(Alignment.Center), text = title)

                if (hasGotRightSubNavigation)
                    Icon(
                        modifier = Modifier.padding(end = 8.dp).clickable { onDrawerClicked() },
                        imageVector = Icons.Default.Menu,
                        tint = Color(textIconActiveColor),
                        contentDescription = null
                    )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(backgroundColor),
            titleContentColor = Color(textIconActiveColor)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color(textIconActiveColor),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
