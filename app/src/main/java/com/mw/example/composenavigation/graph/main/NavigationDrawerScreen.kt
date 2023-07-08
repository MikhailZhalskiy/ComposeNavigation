package com.mw.example.composenavigation.graph.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.App
import com.mw.example.composenavigation.graph.EventBusNavigation
import com.mw.example.composenavigation.graph.NavigationEvent
import com.mw.example.composenavigation.graph.Screen
import com.mw.example.composenavigation.graph.main.backdrop.NavigationBackdropScreen
import com.mw.example.composenavigation.graph.main.bottom_app_bar.NavigationBottomAppBarScreen
import com.mw.example.composenavigation.graph.main.bottom_sheet.NavigationBottomSheetScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.NavigationBarScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.addCallGraph
import com.mw.example.composenavigation.graph.main.tab.NavigationTabScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerScreen(
    navigateCallDetailScreen: (String) -> Unit = {},
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val items = listOf(
        DrawerItem("NavigationBar", Icons.Filled.Menu, Screen.NavigationBar),
        DrawerItem("Tab", Icons.Filled.Face, Screen.NavigationTabBar),
        DrawerItem("BottomSheet", Icons.Filled.ShoppingCart, Screen.NavigationBottomSheet),
        DrawerItem("Backdrop", Icons.Filled.AccountBox, Screen.NavigationBackdrop),
        DrawerItem("BottomAppBar", Icons.Filled.ExitToApp, Screen.NavigationBottomAppBar),
        DrawerItem("FavoriteFeatureApi", Icons.Filled.Favorite, Screen.FeatureFavorite),
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val navController = rememberNavController()

    BackHandler(drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selected = index == selectedIndex,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.screen.route()) {
                                popUpTo(items[selectedIndex].screen.route()) {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                selectedIndex = index
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.NavigationBar.route()) {

            composable(route = Screen.NavigationBar.route()) {
                NavigationBarScreen(
                    navigateCallDetailScreen = navigateCallDetailScreen
                )
            }

            composable(route = Screen.NavigationTabBar.route()) {
                NavigationTabScreen(
                    navigateEmailDetailScreen = {
                        navController.navigate(Screen.EmailDetail.createRouteWithArgs(it))
                    },
                    navigateCallDetailScreen = navigateCallDetailScreen,
                    navigateEventDetailScreen = {
                        scope.launch {
                            EventBusNavigation.send(NavigationEvent.Event(it))
                        }
                    },
                    menuActionButtonClick = { scope.launch { drawerState.open() } }
                )
            }

            composable(
                route = Screen.EmailDetail.route(),
                arguments = Screen.EmailDetail.arguments()
            ) { navBackStackEntry ->
                val email =
                    navBackStackEntry.arguments?.getString(Screen.EmailDetail.emailArg).orEmpty()
                DetailEmailScreen(email)
            }

            composable(route = Screen.NavigationBottomSheet.route()) {
                NavigationBottomSheetScreen()
            }

            composable(route = Screen.NavigationBackdrop.route()) {
                NavigationBackdropScreen(navigateCallDetailScreen = navigateCallDetailScreen)
            }

            composable(route = Screen.NavigationBottomAppBar.route()) {
                NavigationBottomAppBarScreen(
                    navigateCallListScreen = {
                        navController.navigate(Screen.CallList.route())
                    }
                )
            }

            addCallGraph(navigateCallDetailScreen = navigateCallDetailScreen)

//            App.instance.addFeatureFavoriteGraph(this, navController)

//            App.instance.getFeatureDestination().composableFeatureDestination(this, navController)

            composable(route = Screen.FeatureFavorite.route()) {
                App.instance.getFlowScreen()()
            }
        }
    }
}

data class DrawerItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)
