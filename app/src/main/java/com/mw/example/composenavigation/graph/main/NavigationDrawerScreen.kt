package com.mw.example.composenavigation.graph.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mw.example.composenavigation.App
import com.mw.example.composenavigation.graph.EventBusNavigation
import com.mw.example.composenavigation.graph.NavigationEvent
import com.mw.example.composenavigation.graph.Screen
import com.mw.example.composenavigation.graph.main.backdrop.NavigationBackdropScreen
import com.mw.example.composenavigation.graph.main.bottom_app_bar.NavigationBottomAppBarScreen
import com.mw.example.composenavigation.graph.main.bottom_sheet.NavigationBottomSheetScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.NavigationBarScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.addCallGraph
import com.mw.example.composenavigation.graph.main.tab.NavigationTabScreen
import kotlinx.coroutines.launch

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
        DrawerItem("BottomAppBar", Icons.Filled.AccountCircle, Screen.NavigationBottomAppBar),
        DrawerItem("FavoriteFeatureApi", Icons.Filled.Favorite, Screen.FeatureFavorite),
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

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
                            navController.navigate(item.screen) {
                                popUpTo(items[selectedIndex].screen) {
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
        NavHost(
            navController = navController,
            startDestination = Screen.NavigationBar
        ) {

            composable<Screen.NavigationBar> {
                NavigationBarScreen(
                    navigateCallDetailScreen = navigateCallDetailScreen
                )
            }

            composable<Screen.NavigationTabBar> {
                NavigationTabScreen(
                    navigateEmailDetailScreen = {
                        navController.navigate(Screen.EmailDetail(it))
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

            composable<Screen.EmailDetail> { navBackStackEntry ->
                val email: Screen.EmailDetail = navBackStackEntry.toRoute()
                DetailEmailScreen(email.email)
            }

            composable<Screen.NavigationBottomSheet> {
                NavigationBottomSheetScreen()
            }

            composable<Screen.NavigationBackdrop> {
                NavigationBackdropScreen(navigateCallDetailScreen = navigateCallDetailScreen)
            }

            composable<Screen.NavigationBottomAppBar> {
                NavigationBottomAppBarScreen(
                    navigateCallListScreen = {
                        navController.navigate(Screen.CallList)
                    }
                )
            }

            addCallGraph(navigateCallDetailScreen = navigateCallDetailScreen)

//            App.instance.addFeatureFavoriteGraph(this, navController)

//            App.instance.getFeatureDestination().composableFeatureDestination(this, navController)

            composable<Screen.FeatureFavorite> {
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
