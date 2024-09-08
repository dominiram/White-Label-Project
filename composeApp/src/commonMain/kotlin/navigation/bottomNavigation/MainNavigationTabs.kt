package navigation.bottomNavigation

import org.jetbrains.compose.resources.DrawableResource
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

sealed class DestinationRoutes(
    open val route: String,
    open var title: String,
    open val tabIcon: DrawableResource
) {
    sealed class MainNavigationRoutes(
        override val route: String,
        override var title: String,
        override var tabIcon: DrawableResource
    ) : DestinationRoutes(route, title, tabIcon) {

        data object Home : MainNavigationRoutes(
            route = "HOME",
            title = "Home",
            tabIcon = Res.drawable.ic_home,
        )

        data object Search : MainNavigationRoutes(
            route = "SEARCH",
            title = "Search",
            tabIcon = Res.drawable.ic_search,
        )

        data object News : MainNavigationRoutes(
            route = "NEWS",
            title = "News",
            tabIcon = Res.drawable.ic_news,
        )
    }

    data object NewsDetails : DestinationRoutes(
        route = "NEWS_DETAILS",
        title = "NewsDetails",
        tabIcon = Res.drawable.ic_news,
    )
}
