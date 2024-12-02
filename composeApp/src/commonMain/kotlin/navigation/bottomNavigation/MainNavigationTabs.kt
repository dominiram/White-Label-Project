package navigation.bottomNavigation

import org.jetbrains.compose.resources.DrawableResource
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

sealed class DestinationRoutes(
    open val route: String,
    open var title: String,
    open val icon: String,
    open val tabIcon: DrawableResource,
) {
    sealed class MainNavigationRoutes(
        override val route: String,
        override var title: String,
        override var icon: String,
        override var tabIcon: DrawableResource,
    ) : DestinationRoutes(route, title, icon, tabIcon) {

        data object Home : MainNavigationRoutes(
            route = "HOME",
            title = "Home",
            icon = "ic_home",
            tabIcon = Res.drawable.ic_home,
        )

        data object Search : MainNavigationRoutes(
            route = "SEARCH",
            title = "Search",
            icon = "ic_search",
            tabIcon = Res.drawable.ic_search,
        )

        data object News : MainNavigationRoutes(
            route = "NEWS",
            title = "News",
            icon = "ic_news",
            tabIcon = Res.drawable.ic_news,
        )

        data object WebView: MainNavigationRoutes(
            route = "WEB_VIEW",
            title = "Web View",
            icon = "ic_home",
            tabIcon = Res.drawable.ic_home,
        )
    }

    data object NewsDetails : DestinationRoutes(
        route = "NEWS_DETAILS",
        title = "NewsDetails",
        icon = "ic_news",
        tabIcon = Res.drawable.ic_news,
    )

    data class GenericTabItem(
        override val route: String,
        override var title: String,
        override var icon: String,
        override val tabIcon: DrawableResource
    ): DestinationRoutes(route, title, icon, tabIcon)
}
