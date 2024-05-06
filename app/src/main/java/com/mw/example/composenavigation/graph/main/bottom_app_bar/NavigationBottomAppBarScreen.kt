package com.mw.example.composenavigation.graph.main.bottom_app_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mw.example.composenavigation.graph.Screen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailListScreen

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
                            navController.navigate(Screen.EmailList) {
                                popUpTo(Screen.EmailList)
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
            startDestination = Screen.EmailList,
            modifier = Modifier.padding(it)
        ) {
            composable<Screen.EmailList> {
                EmailListScreen() {
                    navController.navigate(Screen.EmailDetail(it))
                }
            }

            composable<Screen.EmailDetail> { navBackStackEntry ->
                val email: Screen.EmailDetail = navBackStackEntry.toRoute()
                DetailEmailScreen(email.email)
            }
        }
    }
}
