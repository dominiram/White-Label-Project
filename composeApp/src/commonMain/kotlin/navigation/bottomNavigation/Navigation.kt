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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import screens.home.WebViewScreen

@Composable
fun MainBottomNavigation() {
    val navController = rememberNavController()
    val homeItem = DestinationRoutes.MainNavigationRoutes.Home
    val reelsItem = DestinationRoutes.MainNavigationRoutes.News
    val webViewItem = DestinationRoutes.MainNavigationRoutes.WebView
    val profileItem = DestinationRoutes.MainNavigationRoutes.Search

    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

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
                    route = reelsItem.route,
                    url = "https://github.com/",
                    name = reelsItem.title
                ),
                NavigationItem(
                    route = webViewItem.route,
                    url = "https://youtube.com/",
                    name = webViewItem.title
                )
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
        drawerState = drawerState,
        scope = scope,
        navController = navController,
        onNavigate = { routeName ->
            navigateTo(routeName, navController)
            scope.launch { drawerState.close() }
        },
        mainNavigationItems = navigationItems
    )
}

@Composable
fun NavHostMain(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
    mainNavigationItems: List<MainNavigationItem> = arrayListOf()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination
    var selectedTabItem: MainNavigationItem = remember { mainNavigationItems[0] }

    Scaffold(
        topBar = {
            val title = getTitle(currentScreen)

            TopBar(
                title = title,
                canNavigateBack = currentScreen?.route?.isNotMainNavigationRoute() == true,
                hasGotLeftSubNavigation = true,
                hasGotRightSubNavigation = false,
                onDrawerClicked = { scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } },
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                mainNavigationItems = mainNavigationItems,
                closeNavigationDrawer = { scope.launch { drawerState.close() } }
            )
        }
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
            mainNavigationItems.forEach { navigationItem ->
                navigationItem.subCategories.takeIf { it.isNotEmpty() }?.forEach { item ->
                    composable(route = item.route) {
                        if (navController.currentBackStackEntry?.destination?.route == item.route)
                            selectedTabItem = navigationItem

                        when {
                            item.isWebView() -> WebViewScreen(
                                webViewUrl = navigationItem.url,
                                onNavigate = { routeName ->
                                    onNavigate(routeName)
                                    scope.launch { drawerState.close() }
                                },
                                subCategories = navigationItem.subCategories,
                                drawerState = drawerState
                            )

                            else -> HomeScreen(
                                webViewUrl = navigationItem.url,
                                onNavigate = { routeName ->
                                    onNavigate(routeName)
                                    scope.launch { drawerState.close() }
                                },
                                subCategories = navigationItem.subCategories,
                                drawerState = drawerState
                            )
                        }
                    }
                } ?: composable(route = navigationItem.route) {
                    if (navController.currentBackStackEntry?.destination?.route == navigationItem.route)
                        selectedTabItem = navigationItem

                    HomeScreen(
                        webViewUrl = navigationItem.url,
                        onNavigate = { routeName ->
                            onNavigate(routeName)
                            scope.launch { drawerState.close() }
                        },
                        subCategories = navigationItem.subCategories,
                        drawerState = drawerState
                    )
                }
            }
        }
    }
}
