package navigation.bottomNavigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import models.MainNavigationItem
import navigation.bottomNavigation.Constants.BACK_CLICK_ROUTE

fun navigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
        BACK_CLICK_ROUTE -> navController.popBackStack()
        else -> navController.navigate(routeName)
    }
}

fun getTitle(currentScreen: NavDestination?): String = when (currentScreen?.route) {
    DestinationRoutes.MainNavigationRoutes.Home.route -> "Home"
    DestinationRoutes.MainNavigationRoutes.News.route -> "News"
    DestinationRoutes.MainNavigationRoutes.Search.route -> "Search"
    else -> ""
}

fun String?.isNotMainNavigationRoute(): Boolean =
    this != DestinationRoutes.MainNavigationRoutes.Home.route &&
            this != DestinationRoutes.MainNavigationRoutes.News.route &&
            this != DestinationRoutes.MainNavigationRoutes.Search.route

fun NavController.shouldShowBottomBar() =
    when (this.currentBackStackEntry?.destination?.route) {
        DestinationRoutes.MainNavigationRoutes.Home.route,
        DestinationRoutes.MainNavigationRoutes.News.route,
        DestinationRoutes.MainNavigationRoutes.Search.route,
        DestinationRoutes.MainNavigationRoutes.WebView.route -> true

        else -> false
    }

fun navigateBottomBar(navController: NavController, destination: String) =
    navController.navigate(destination) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(DestinationRoutes.MainNavigationRoutes.Home.route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }

fun isItemSelected(navController: NavHostController, item: MainNavigationItem): Boolean =
    navController.currentBackStackEntry?.destination?.route == item.route ||
            item.subCategories?.find { navController.currentBackStackEntry?.destination?.route == it.route } != null
