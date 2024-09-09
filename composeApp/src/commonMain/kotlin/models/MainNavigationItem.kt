package models

import org.jetbrains.compose.resources.DrawableResource

data class MainNavigationItem(
    val route: String,
    val url: String,
    val name: String,
    val tabIcon: DrawableResource,
    val subCategories: List<NavigationItem>,
)

data class NavigationItem(
    val route: String,
    val url: String,
    val name: String,
)
