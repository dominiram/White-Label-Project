package navigation.bottomNavigation

import androidx.navigation.NavController
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

fun navigateBottomBar(navController: NavController, beginning: String, destination: String) =
    navController.navigate(destination) {

        navController.graph.startDestinationRoute?.let {
            popUpTo(beginning) {
                saveState = true
            }
        }

        launchSingleTop = true
        restoreState = true
    }

fun isItemSelected(navController: NavHostController, item: MainNavigationItem): Boolean =
    navController.currentBackStackEntry?.destination?.route?.substringBeforeLast('/') == item.route
