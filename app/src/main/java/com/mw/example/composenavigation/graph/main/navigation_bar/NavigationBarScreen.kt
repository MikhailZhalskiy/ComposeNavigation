package com.mw.example.composenavigation.graph.main.navigation_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.mw.example.composenavigation.graph.*
import com.mw.example.composenavigation.graph.main.navigation_bar.call.CallListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.event.EventListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

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
                        navController.navigate(items[0].screen.route()) {
                            popUpTo(items[selectedIndex].screen.route()) {
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
                        navController.navigate(items[1].screen.route()) {
                            popUpTo(items[selectedIndex].screen.route()) {
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
                        navController.navigate(items[2].screen.route()) {
                            popUpTo(items[selectedIndex].screen.route()) {
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
                        navController.navigate(items[3].screen.route()) {
                            popUpTo(items[selectedIndex].screen.route()) {
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
            startDestination = Navigation.EmailList.route(),
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
    navigation(startDestination = Screen.EmailList.route(), route = Navigation.EmailList.route()) {

        composable(route = Screen.EmailList.route()) {
            EmailListScreen(
                navigateEmailDetailScreen = {
                    navController.navigate(Screen.EmailDetail.createRouteWithArgs(it))
                }
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
    }
}

fun NavGraphBuilder.addFavoriteGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.FavoriteList.route(),
        route = Navigation.FavoriteList.route()
    ) {
        composable(route = Screen.FavoriteList.route()) {
            FavoriteListScreen(
                navigateFavoriteDetailScreen = {
                    navController.navigate(Screen.FavoriteDetail.createRouteWithArgs(it))
                }
            )
        }

        composable(
            route = Screen.FavoriteDetail.route(),
            arguments = Screen.FavoriteDetail.arguments()
        ) { navBackStackEntry ->
            val favorite =
                navBackStackEntry.arguments?.getString(Screen.FavoriteDetail.favoriteArg).orEmpty()
            DetailFavoriteScreen(favorite)
        }
    }
}

fun NavGraphBuilder.addCallGraph(
    navigateCallDetailScreen: (String) -> Unit = {},
) {
    navigation(startDestination = Screen.CallList.route(), route = Navigation.CallList.route()) {
        composable(route = Screen.CallList.route()) {
            CallListScreen(
                navigateCallDetailScreen = navigateCallDetailScreen
            )
        }
    }
}

fun NavGraphBuilder.addEventGraph(
    navigateEventDetailScreen: (String) -> Unit = {},
) {
    navigation(startDestination = Screen.EventList.route(), route = Navigation.EventList.route()) {
        composable(route = Screen.EventList.route()) {
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