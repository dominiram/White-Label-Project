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
import org.koin.compose.viewmodel.koinViewModel
import screens.home.HomeScreen
import screens.home.WebViewScreen
import utils.Constants.ENTER_DURATION
import utils.toColor

@Composable
fun MainBottomNavigation() {
    val viewModel = koinViewModel<BottomNavigationViewModel>()
    val navController = rememberNavController()

    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    val navigationItems = viewModel.getBottomNavigationItems()
    val topBarBackgroundColor = viewModel.getTopBarBackgroundColor().toColor()
    val topBarTextIconColor = viewModel.getTopBarTextIconColor().toColor()
    val mainNavigationBackgroundColor = viewModel.getMainNavigationBackgroundColor().toColor()

    val mainNavigationTextIconActiveColor =
        viewModel.getMainNavigationSelectedTextIconColor().toColor()

    val mainNavigationTextIconInactiveColor =
        viewModel.getMainNavigationUnselectedTextIconColor().toColor()

    val sideNavigationBackgroundColor = viewModel.getSideNavigationBackgroundColor().toColor()

    val sideNavigationTextIconActiveColor =
        viewModel.getSideNavigationSelectedTextIconColor().toColor()

    val sideNavigationTextIconInactiveColor =
        viewModel.getSideNavigationUnselectedTextIconColor().toColor()

    NavHostMain(
        drawerState = drawerState,
        scope = scope,
        navController = navController,
        onNavigate = { routeName ->
            navigateTo(routeName, navController)
            scope.launch { drawerState.close() }
        },
        mainNavigationItems = navigationItems,
        mainNavigationBackgroundColor = mainNavigationBackgroundColor,
        topBarBackgroundColor = topBarBackgroundColor,
        topBarTextIconColor = topBarTextIconColor,
        mainNavigationTextIconActiveColor = mainNavigationTextIconActiveColor,
        mainNavigationTextIconInactiveColor = mainNavigationTextIconInactiveColor,
        sideNavigationBackgroundColor = sideNavigationBackgroundColor,
        sideNavigationTextIconActiveColor = sideNavigationTextIconActiveColor,
        sideNavigationTextIconInactiveColor = sideNavigationTextIconInactiveColor,
    )
}

@Composable
fun NavHostMain(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
    mainNavigationItems: List<MainNavigationItem> = arrayListOf(),
    topBarBackgroundColor: Long,
    topBarTextIconColor: Long,
    mainNavigationBackgroundColor: Long,
    mainNavigationTextIconActiveColor: Long,
    mainNavigationTextIconInactiveColor: Long,
    sideNavigationBackgroundColor: Long,
    sideNavigationTextIconActiveColor: Long,
    sideNavigationTextIconInactiveColor: Long
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
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
                backgroundColor = topBarBackgroundColor,
                textIconActiveColor = topBarTextIconColor,
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
                    navigateBottomBar(
                        navController = navController,
                        beginning = mainNavigationItems[0].route,
                        destination = route
                    )?.let {
                        mainNavigationItems.find { it.getFirstSubcategoryRoute() == route }?.let {
                            selectedTabItem = it
                        }
                    }
                },
                isItemSelected = { item -> isItemSelected(selectedTabItem, item) },
                backgroundColor = mainNavigationBackgroundColor,
                textIconActiveColor = mainNavigationTextIconActiveColor,
                textIconInactiveColor = mainNavigationTextIconInactiveColor,
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
                                        progressColor = mainNavigationBackgroundColor,
                                        onNavigate = { routeName -> onNavigate(routeName) },
                                        subCategories = navigationItem.subCategories
                                            ?: arrayListOf(),
                                        drawerState = drawerState,
                                        backgroundColor = sideNavigationBackgroundColor,
                                        textIconActiveColor = sideNavigationTextIconActiveColor,
                                        textIconInactiveColor = sideNavigationTextIconInactiveColor
                                    )
                                }

                                else -> item.url?.let { webViewUrl ->
                                    HomeScreen(
                                        webViewUrl = webViewUrl,
                                        progressColor = mainNavigationBackgroundColor,
                                        onNavigate = { routeName -> onNavigate(routeName) },
                                        subCategories = navigationItem.subCategories
                                            ?: arrayListOf(),
                                        drawerState = drawerState,
                                        backgroundColor = sideNavigationBackgroundColor,
                                        textIconActiveColor = sideNavigationTextIconActiveColor,
                                        textIconInactiveColor = sideNavigationTextIconInactiveColor
                                    )
                                }
                            }
                        }
                    }
                } ?: composable(route = navigationItem.route) {
                    navigationItem.url?.let { webViewUrl ->
                        HomeScreen(
                            webViewUrl = webViewUrl,
                            progressColor = mainNavigationBackgroundColor,
                            onNavigate = { routeName -> onNavigate(routeName) },
                            subCategories = navigationItem.subCategories ?: arrayListOf(),
                            drawerState = drawerState,
                            backgroundColor = sideNavigationBackgroundColor,
                            textIconActiveColor = sideNavigationTextIconActiveColor,
                            textIconInactiveColor = sideNavigationTextIconInactiveColor
                        )
                    }
                }
            }
        }
    }
}
