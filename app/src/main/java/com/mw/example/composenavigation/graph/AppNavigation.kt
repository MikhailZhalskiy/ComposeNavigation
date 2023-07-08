package com.mw.example.composenavigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.graph.main.NavigationDrawerScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.call.DetailCallScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.event.DetailEventScreen
import com.mw.example.composenavigation.graph.start.LoginScreen
import com.mw.example.composenavigation.graph.start.RegistrationScreen
import com.mw.example.composenavigation.graph.start.SplashScreen
import com.mw.example.composenavigation.graph.start.WelcomeScreen
import com.mw.example.composenavigation.ui.theme.ComposeNavigationTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    LaunchedEffect(key1 = Unit) {
        EventBusNavigation.receiver()
            .onEach {
                when(it) {
                    is NavigationEvent.Event -> {
                        navController.navigate(Screen.EventDetail.createRouteWithArgs(it.event))
                    }
                }
            }
            .collect()
    }

    ComposeNavigationTheme {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route(),
        ) {
            composable(
                route = Screen.Splash.route()
            ) {
                SplashScreen(
                    navigateWelcomeScreen = {
                        navController.navigate(Screen.Welcome.route()) {
                            popUpTo(Screen.Splash.route()) {
                                inclusive = true
                            }
                        }
                    },
                    navigateLoginScreen = {
                        navController.navigate(Screen.Login.route()) {
                            popUpTo(Screen.Splash.route()) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.Welcome.route()
            ) {
                WelcomeScreen()
            }

            composable(
                route = Screen.Login.route()
            ) {
                LoginScreen(
                    navigateMainScreen = {
                        navController.navigate(Screen.NavigationDrawer.route()) {
                            popUpTo(Screen.Login.route()) {
                                inclusive = true
                            }
                        }
                    },
                    navigateRegistrationScreen = {
                        navController.navigate(Screen.Registration.route())
                    }
                )
            }

            composable(
                route = Screen.Registration.route()
            ) {
                RegistrationScreen(
                    navigateMainScreen = {
                        navController.navigate(Screen.NavigationDrawer.route()){
                            popUpTo(Screen.Login.route()) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.NavigationDrawer.route()
            ) {
                NavigationDrawerScreen(
                    navigateCallDetailScreen = {
                        navController.navigate(Screen.CallDetail.createRouteWithArgs(it))
                    }
                )
            }

            composable(
                route = Screen.CallDetail.route(),
                arguments = Screen.CallDetail.arguments()
            ) { navBackStackEntry ->
                val call = navBackStackEntry.arguments?.getString(Screen.CallDetail.callArg).orEmpty()
                DetailCallScreen(call)
            }

            composable(
                route = Screen.EventDetail.route(),
                arguments = Screen.EventDetail.arguments()
            ) { navBackStackEntry ->
                val event = navBackStackEntry.arguments?.getString(Screen.EventDetail.eventArg).orEmpty()
                DetailEventScreen(event)
            }
        }
    }
}