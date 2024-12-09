package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.NavigationItemConstants.ICON_PATH_HOME
import models.NavigationItemConstants.ICON_PATH_NEWS
import models.NavigationItemConstants.ICON_PATH_SEARCH
import navigation.bottomNavigation.Constants.WEB_VIEW_ROUTE
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.compose_multiplatform
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

@Serializable
data class MainNavigationItem(
    @SerialName("id") val id: Long? = null,
    @SerialName("slug") val route: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("title") val name: String? = null,
    @SerialName("icon") val icon: String? = null,
    @SerialName("right_menu") val subCategories: List<NavigationItem>? = arrayListOf(),
) {
    @Contextual
    val tabIcon = when(icon) {
        ICON_PATH_HOME -> Res.drawable.ic_home
        ICON_PATH_NEWS -> Res.drawable.ic_news
        ICON_PATH_SEARCH -> Res.drawable.ic_search
        else -> Res.drawable.compose_multiplatform
    }
}

@Serializable
data class NavigationItem(
    @SerialName("id") val id: Long? = null,
    @SerialName("slug") val route: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("title") val name: String? = null,
    @SerialName("icon") val icon: String? = null,
) {
    fun isWebView() = route == WEB_VIEW_ROUTE
}
