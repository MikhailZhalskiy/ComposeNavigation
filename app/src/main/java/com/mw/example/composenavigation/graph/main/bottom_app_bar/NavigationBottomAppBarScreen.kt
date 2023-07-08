package com.mw.example.composenavigation.graph.main.bottom_app_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.graph.Screen
import com.mw.example.composenavigation.graph.main.navigation_bar.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.EmailListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBottomAppBarScreen(
    navigateCallListScreen: () -> Unit = {},
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.EmailList.route()) {
                                popUpTo(Screen.EmailList.route())
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Email, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { navigateCallListScreen() }) {
                        Icon(Icons.Filled.Call, contentDescription = "Localized description")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.EmailList.route(),
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screen.EmailList.route()) {
                EmailListScreen() {
                    navController.navigate(Screen.EmailDetail.createRouteWithArgs(it))
                }
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
}
