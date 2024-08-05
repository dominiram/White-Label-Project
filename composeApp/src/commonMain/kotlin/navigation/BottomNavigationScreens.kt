package navigation

import org.jetbrains.compose.resources.DrawableResource
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

sealed class BottomBarScreen(
    val route: String,
    var title: String,
    val defaultIcon: DrawableResource
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        defaultIcon = Res.drawable.ic_home,
    )

    data object Search : BottomBarScreen(
        route = "SEARCH",
        title = "Search",
        defaultIcon = Res.drawable.ic_search,
    )

    data object News : BottomBarScreen(
        route = "NEWS",
        title = "News",
        defaultIcon = Res.drawable.ic_news,
    )
}
