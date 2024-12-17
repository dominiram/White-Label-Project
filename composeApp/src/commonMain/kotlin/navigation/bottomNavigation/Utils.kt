package navigation.bottomNavigation

import androidx.navigation.NavController
import models.MainNavigationItem
import utils.Constants.BACK_CLICK_ROUTE

fun navigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
        BACK_CLICK_ROUTE -> navController.popBackStack()
        else -> navController.navigate(routeName)
    }
}

fun navigateBottomBar(navController: NavController, beginning: String, destination: String): Unit? =
    if (navController.findDestination(destination) != null)
        navController.navigate(destination) {

            navController.graph.startDestinationRoute?.let {
                popUpTo(beginning) {
                    saveState = true
                }
            }

            launchSingleTop = true
            restoreState = true
        } else null

fun isItemSelected(selectedItem: MainNavigationItem, item: MainNavigationItem): Boolean =
    selectedItem.route == item.route.substringBeforeLast('/')
