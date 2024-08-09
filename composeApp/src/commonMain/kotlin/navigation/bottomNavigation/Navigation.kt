package navigation.bottomNavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import navigation.bottomNavigation.Constants.ENTER_DURATION
import navigation.bottomNavigation.Constants.WEB_VIEW_URL_NAV_ARG
import screens.home.HomeScreen
import screens.news.NewsScreen
import screens.search.SearchScreen

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
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination

    Scaffold(
        topBar = {
            val title = getTitle(currentScreen)

            TopBar(
                title = title,
                canNavigateBack = currentScreen?.route?.isNotMainNavigationRoute() == true,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "${DestinationRoutes.MainNavigationRoutes.Home.route}/$WEB_VIEW_URL_NAV_ARG",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ENTER_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ENTER_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ENTER_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ENTER_DURATION)
                )
            }
        ) {
            composable(
                route = "${DestinationRoutes.MainNavigationRoutes.Home.route}/{$WEB_VIEW_URL_NAV_ARG}",
                arguments = listOf(navArgument(WEB_VIEW_URL_NAV_ARG) { type = NavType.StringType })
            ) { backStackEntry ->

                val homeNavigationTest = "https://github.com/KevinnZou/compose-webview-multiplatform"
                val webViewUrl = backStackEntry.arguments?.getString(WEB_VIEW_URL_NAV_ARG) ?: homeNavigationTest

                HomeScreen(
                    webViewUrl = webViewUrl,
                    onNavigate = onNavigate
                )
            }

            composable(route = DestinationRoutes.MainNavigationRoutes.News.route) {
                NewsScreen(onNavigate = onNavigate)
            }

            composable(route = DestinationRoutes.MainNavigationRoutes.Search.route) {
                SearchScreen(onNavigate = onNavigate)
            }
        }
    }
}
