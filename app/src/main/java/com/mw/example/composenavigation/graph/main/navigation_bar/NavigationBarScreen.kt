package com.mw.example.composenavigation.graph.main.navigation_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mw.example.composenavigation.graph.EventBusNavigation
import com.mw.example.composenavigation.graph.Navigation
import com.mw.example.composenavigation.graph.NavigationEvent
import com.mw.example.composenavigation.graph.Screen
import com.mw.example.composenavigation.graph.main.navigation_bar.call.CallListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.event.EventListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.favorite.DetailFavoriteScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.favorite.FavoriteListScreen
import kotlinx.coroutines.launch

@Composable
fun NavigationBarScreen(
    navigateCallDetailScreen: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val items = listOf(
        BarItem("Email", Icons.Filled.Email, Navigation.EmailList),
        BarItem("Favorite", Icons.Filled.Favorite, Navigation.FavoriteList),
        BarItem("Call", Icons.Filled.Call, Navigation.CallList),
        BarItem("Event", Icons.Filled.Share, Navigation.EventList),
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

//    val printBackStack by remember {
//        derivedStateOf {
//            println("-----start----")
//            navController.backQueue.forEach {
//                println(it.destination.route)
//            }
//            println("-----end----")
//        }
//    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(items[0].icon, contentDescription = null) },
                    label = { Text(items[0].label) },
                    selected = selectedIndex == 0,
                    onClick = {
                        navController.navigate(items[0].screen) {
                            popUpTo(items[selectedIndex].screen) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                            selectedIndex = 0
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(items[1].icon, contentDescription = null) },
                    label = { Text(items[1].label) },
                    selected = selectedIndex == 1,
                    onClick = {
                        navController.navigate(items[1].screen) {
                            popUpTo(items[selectedIndex].screen) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            selectedIndex = 1
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(items[2].icon, contentDescription = null) },
                    label = { Text(items[2].label) },
                    selected = selectedIndex == 2,
                    onClick = {
                        navController.navigate(items[2].screen) {
                            popUpTo(items[selectedIndex].screen) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                            selectedIndex = 2
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(items[3].icon, contentDescription = null) },
                    label = { Text(items[3].label) },
                    selected = selectedIndex == 3,
                    onClick = {
                        navController.navigate(items[3].screen) {
                            popUpTo(items[selectedIndex].screen) {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                            selectedIndex = 3
                        }
                    }
                )

            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Navigation.EmailList,
            modifier = Modifier.padding(it)
        ) {
            addEmailGraph(navController)
            addFavoriteGraph(navController)
            addCallGraph(navigateCallDetailScreen)
            addEventGraph {
                scope.launch {
                    EventBusNavigation.send(NavigationEvent.Event(it))
                }
            }
        }
    }
}

fun NavGraphBuilder.addEmailGraph(navController: NavHostController) {
    navigation<Navigation.EmailList>(startDestination = Screen.EmailList) {

        composable<Screen.EmailList> {
            EmailListScreen(
                navigateEmailDetailScreen = {
                    navController.navigate(Screen.EmailDetail(it))
                }
            )
        }

        composable<Screen.EmailDetail> { navBackStackEntry ->
            val email: Screen.EmailDetail = navBackStackEntry.toRoute()
            DetailEmailScreen(email.email)
        }
    }
}

fun NavGraphBuilder.addFavoriteGraph(
    navController: NavHostController,
) {
    navigation<Navigation.FavoriteList>(startDestination = Screen.FavoriteList,) {
        composable<Screen.FavoriteList> {
            FavoriteListScreen(
                navigateFavoriteDetailScreen = {
                    navController.navigate(Screen.FavoriteDetail(it))
                }
            )
        }

        composable<Screen.FavoriteDetail> { navBackStackEntry ->
            val favorite: Screen.FavoriteDetail = navBackStackEntry.toRoute()
            DetailFavoriteScreen(favorite.email)
        }
    }
}

fun NavGraphBuilder.addCallGraph(
    navigateCallDetailScreen: (String) -> Unit = {},
) {
    navigation<Navigation.CallList>(startDestination = Screen.CallList) {
        composable<Screen.CallList> {
            CallListScreen(
                navigateCallDetailScreen = navigateCallDetailScreen
            )
        }
    }
}

fun NavGraphBuilder.addEventGraph(
    navigateEventDetailScreen: (String) -> Unit = {},
) {
    navigation<Navigation.EventList>(startDestination = Screen.EventList) {
        composable<Screen.EventList> {
            EventListScreen(
                navigateEventDetailScreen = navigateEventDetailScreen
            )
        }
    }
}

data class BarItem(
    val label: String,
    val icon: ImageVector,
    val screen: Navigation
)