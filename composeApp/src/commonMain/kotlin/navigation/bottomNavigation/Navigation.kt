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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.MainNavigationItem
import navigation.bottomNavigation.Constants.ENTER_DURATION
import org.koin.compose.viewmodel.koinViewModel
import screens.home.HomeScreen
import screens.home.WebViewScreen

@Composable
fun MainBottomNavigation() {
    val viewModel = koinViewModel<BottomNavigationViewModel>()
    val navController = rememberNavController()

    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    val navigationItems = viewModel.getBottomNavigationItems()

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
    var selectedTabItem: MainNavigationItem by remember { mutableStateOf(mainNavigationItems[0]) }

    val selectedTabItemTitle by remember(selectedTabItem) {
        mutableStateOf(
            selectedTabItem.name ?: ""
        )
    }

    val shouldShowBottomAppBar =
        remember(backStackEntry) { mainNavigationItems.find { it == selectedTabItem } != null }

    Scaffold(
        topBar = {
            TopBar(
                title = selectedTabItemTitle,
                canNavigateBack = !shouldShowBottomAppBar,
                hasGotLeftSubNavigation = !selectedTabItem.leftSubCategories.isNullOrEmpty(),
                hasGotRightSubNavigation = !selectedTabItem.rightSubCategories.isNullOrEmpty(),
                onDrawerClicked = { scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } },
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                mainNavigationItems = mainNavigationItems,
                shouldShowBottomAppBar = shouldShowBottomAppBar,
                navigateBottomBar = { route ->
                    mainNavigationItems.find { it.getFirstSubcategoryRoute() == route }?.let {
                        selectedTabItem = it
                    }

                    navigateBottomBar(
                        navController = navController,
                        beginning = mainNavigationItems[0].route,
                        destination = route
                    )
                },
                isItemSelected = { item -> isItemSelected(navController, item) },
                closeNavigationDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = mainNavigationItems[0].route,
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
                navigationItem.subCategories.takeIf { it?.isNotEmpty() == true }?.forEach { item ->
                    item.route?.let {
                        composable(route = item.getFullRoute()) {
                            when {
                                item.isWebView() -> item.url?.let { webViewUrl ->
                                    WebViewScreen(
                                        webViewUrl = webViewUrl,
                                        onNavigate = { routeName ->
                                            onNavigate(routeName)
                                            scope.launch { drawerState.close() }
                                        },
                                        subCategories = navigationItem.subCategories
                                            ?: arrayListOf(),
                                        drawerState = drawerState
                                    )
                                }

                                else -> item.url?.let { webViewUrl ->
                                    HomeScreen(
                                        webViewUrl = webViewUrl,
                                        onNavigate = { routeName ->
                                            onNavigate(routeName)
                                            scope.launch { drawerState.close() }
                                        },
                                        subCategories = navigationItem.subCategories
                                            ?: arrayListOf(),
                                        drawerState = drawerState
                                    )
                                }
                            }
                        }
                    }
                } ?: composable(route = navigationItem.route) {
                    navigationItem.url?.let { webViewUrl ->
                        HomeScreen(
                            webViewUrl = webViewUrl,
                            onNavigate = { routeName ->
                                onNavigate(routeName)
                                scope.launch { drawerState.close() }
                            },
                            subCategories = navigationItem.subCategories ?: arrayListOf(),
                            drawerState = drawerState
                        )
                    }
                }
            }
        }
    }
}
