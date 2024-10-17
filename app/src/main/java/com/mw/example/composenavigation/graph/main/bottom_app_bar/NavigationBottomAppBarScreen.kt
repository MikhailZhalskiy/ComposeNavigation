package com.mw.example.composenavigation.graph.main.bottom_app_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailDetail
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailList
import com.mw.example.composenavigation.graph.main.navigation_bar.email.emailDetailDestination
import com.mw.example.composenavigation.graph.main.navigation_bar.email.emailListDestination
import kotlinx.serialization.Serializable

@Serializable
internal data object NavigationBottomAppBar

fun NavGraphBuilder.navigationBottomAppBar(
    navigateCallListScreen: () -> Unit = {},
) {
    composable<NavigationBottomAppBar> {
        NavigationBottomAppBarScreen(
            navigateCallListScreen = navigateCallListScreen
        )
    }
}

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
                            navController.navigate(EmailList) {
                                popUpTo(EmailList)
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
            startDestination = EmailList,
            modifier = Modifier.padding(it)
        ) {

            emailListDestination(
                navigateEmailDetailScreen = {
                    navController.navigate(EmailDetail(it))
                }
            )

            emailDetailDestination()
        }
    }
}
