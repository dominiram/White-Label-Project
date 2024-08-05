package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeNav() {
    val navController = rememberNavController()

    NavHostMain(
        navController = navController,
        onNavigate = { routeName ->
            navigateTo(routeName, navController)
        }
    )
}

@Composable
fun NavHostMain(
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
) {}

fun navigateTo(
    routeName: String,
    navController: NavController
) {
    when (routeName) {
        AppConstants.BACK_CLICK_ROUTE -> {
            navController.popBackStack()
        }

        else -> {
            navController.navigate(routeName)
        }
    }
}

