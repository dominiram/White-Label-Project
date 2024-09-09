package navigation.bottomNavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.DrawerState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.MainNavigationItem
import models.NavigationItem
import navigation.bottomNavigation.Constants.ENTER_DURATION
import screens.home.HomeScreen
import screens.news.NewsScreen
import screens.search.SearchScreen

@Composable
fun MainBottomNavigation() {
    val navController = rememberNavController()
    val homeItem = DestinationRoutes.MainNavigationRoutes.Home
    val reelsItem = DestinationRoutes.MainNavigationRoutes.News
    val profileItem = DestinationRoutes.MainNavigationRoutes.Search

    val navigationItems = listOf(
        MainNavigationItem(
            route = homeItem.route,
            url = "https://github.com/KevinnZou/compose-webview-multiplatform",
            name = homeItem.title,
            tabIcon = homeItem.tabIcon,
            subCategories = listOf(
                NavigationItem(
                    route = homeItem.route,
                    url = "https://github.com/KevinnZou/compose-webview-multiplatform",
                    name = homeItem.title
                ),

                NavigationItem(
                    route = homeItem.route,
                    url = "https://github.com/KevinnZou/compose-webview-multiplatform",
                    name = homeItem.title
                ),
            ),
        ),

        MainNavigationItem(
            route = reelsItem.route,
            url = "https://github.com/",
            name = reelsItem.title,
            tabIcon = reelsItem.tabIcon,
            subCategories = arrayListOf(),
        ),

        MainNavigationItem(
            route = profileItem.route,
            url = "https://google.com/",
            name = profileItem.title,
            tabIcon = profileItem.tabIcon,
            subCategories = arrayListOf()
        )
    )

    NavHostMain(
        navController = navController,
        onNavigate = { routeName ->
            navigateTo(routeName, navController)
        },
        navigationItems = emptyList(),
        mainNavigationItems = navigationItems
    )
}

@Composable
fun NavHostMain(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    navigationItems: List<() -> Unit>,
    onNavigate: (rootName: String) -> Unit,
    mainNavigationItems: List<MainNavigationItem> = arrayListOf()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination

    Scaffold(
        topBar = {
            val title = getTitle(currentScreen)

            TopBar(
                title = title,
                canNavigateBack = currentScreen?.route?.isNotMainNavigationRoute() == true,
                hasGotRightSubNavigation = true,
                onDrawerClicked = { scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } },
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = { BottomNavigationBar(navController, mainNavigationItems) }
    ) { innerPadding ->

        NavigationDrawer(
            isLeftSide = true,
            drawerContent = {
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
                    mainNavigationItems.forEach { navigationItem ->
                        composable(route = navigationItem.route) {
                            HomeScreen(navigationItem.url, onNavigate)
                        }
                    }

//                    composable(route = DestinationRoutes.MainNavigationRoutes.Home.route) {
//
//                        val homeNavigationTest =
//                            "https://github.com/KevinnZou/compose-webview-multiplatform"
//
//                        HomeScreen(
//                            webViewUrl = homeNavigationTest,
//                            onNavigate = onNavigate
//                        )
//                    }
//
//                    composable(route = DestinationRoutes.MainNavigationRoutes.News.route) {
//                        NewsScreen(onNavigate = onNavigate)
//                    }
//
//                    composable(route = DestinationRoutes.MainNavigationRoutes.Search.route) {
//                        SearchScreen(onNavigate = onNavigate)
//                    }
                }
            },
            drawerState = drawerState,
            navigationItems = navigationItems
        )
    }
}
