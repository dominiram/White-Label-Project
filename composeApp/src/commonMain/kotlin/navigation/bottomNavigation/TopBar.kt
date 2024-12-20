package navigation.bottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import composables.SubCategory
import models.NavigationItem

@Composable
fun NavigationDrawer(
    isLeftSide: Boolean,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    screenContent: @Composable () -> Unit,
    onNavigationItemClicked: (String) -> Unit,
    navigationItems: List<NavigationItem>
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
                        .background(color = Color.White),
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
                            onClick = { onNavigationItemClicked(subCategory.getFullRoute()) }
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
    backgroundColor: Long,
    textIconActiveColor: Long,
    canNavigateBack: Boolean,
    hasGotLeftSubNavigation: Boolean,
    hasGotRightSubNavigation: Boolean,
    onDrawerClicked: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = if (hasGotLeftSubNavigation) Alignment.CenterStart else Alignment.CenterEnd
            ) {

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
                IconButton(onClick = navigateUp) {
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
