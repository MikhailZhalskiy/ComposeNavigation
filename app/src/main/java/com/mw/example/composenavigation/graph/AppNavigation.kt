package com.mw.example.composenavigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.graph.main.NavigationDrawer
import com.mw.example.composenavigation.graph.main.navigationDrawerDestination
import com.mw.example.composenavigation.graph.main.navigation_bar.call.CallDetail
import com.mw.example.composenavigation.graph.main.navigation_bar.call.callDetailDestination
import com.mw.example.composenavigation.graph.main.navigation_bar.event.EventDetail
import com.mw.example.composenavigation.graph.main.navigation_bar.event.eventDetailDestination
import com.mw.example.composenavigation.graph.start.Login
import com.mw.example.composenavigation.graph.start.LoginGraph
import com.mw.example.composenavigation.graph.start.Registration
import com.mw.example.composenavigation.graph.start.Splash
import com.mw.example.composenavigation.graph.start.Welcome
import com.mw.example.composenavigation.graph.start.addLoginGraph
import com.mw.example.composenavigation.ui.theme.ComposeNavigationTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    LaunchedEffect(key1 = Unit) {
        EventBusNavigation.receiver()
            .onEach {
                when (it) {
                    is NavigationEvent.Event -> {
                        navController.navigate(EventDetail(it.event))
                    }
                }
            }
            .collect()
    }

    ComposeNavigationTheme {
        NavHost(
            navController = navController,
            startDestination = LoginGraph,
        ) {

            addLoginGraph(
                navigateWelcomeScreen = {
                    navController.navigateToWelcome()
                },
                navigateLoginScreen = {
                    navController.navigateToLogin()
                },
                navigateMainScreen = {
                    navController.navigateToNavigationDrawer()
                },
                navigateRegistrationScreen = {
                    navController.navigateToRegistration()
                }
            )

            navigationDrawerDestination(
                navigateCallDetailScreen = { email ->
                    navController.navigate(CallDetail(email))
                }
            )

            callDetailDestination()

            eventDetailDestination()
        }
    }
}

fun NavController.navigateToWelcome() {
    navigate(Welcome) {
        popUpTo(Splash) {
            inclusive = true
        }
    }
}

fun NavController.navigateToLogin() {
    navigate(Login) {
        popUpTo(Splash) {
            inclusive = true
        }
    }
}

fun NavController.navigateToNavigationDrawer() {
    navigate(NavigationDrawer) {
        popUpTo(Login) {
            inclusive = true
        }
    }
}

fun NavController.navigateToRegistration() {
    navigate(Registration)
}