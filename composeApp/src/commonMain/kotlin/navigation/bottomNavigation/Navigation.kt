package navigation.bottomNavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import navigation.bottomNavigation.Constants.ENTER_DURATION
import screens.home.HomeScreen
import screens.news.NewsScreen
import screens.search.SearchScreen

@Composable
fun MainBottomNavigation() {
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
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            //TODO: insert Navigation Drawer (hamburger menu)
            NavigationDrawer(
                isLeftSide = true,
                drawerContent = {
                    TopBar(
                        title = title,
                        canNavigateBack = currentScreen?.route?.isNotMainNavigationRoute() == true,
                        hasGotRightSubNavigation = true,
                        openSubCategories = { scope.launch { drawerState.open() } },
                        navigateUp = { navController.navigateUp() }
                    )
                },
                drawerState = drawerState,
                navigationItems = emptyList()
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = DestinationRoutes.MainNavigationRoutes.Home.route,
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
            composable(route = DestinationRoutes.MainNavigationRoutes.Home.route) {

                val homeNavigationTest =
                    "https://github.com/KevinnZou/compose-webview-multiplatform"

                HomeScreen(
                    webViewUrl = homeNavigationTest,
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
