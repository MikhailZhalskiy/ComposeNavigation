package com.mw.example.composenavigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
                        navController.navigate(Screen.EventDetail(it.event))
                    }
                }
            }
            .collect()
    }

    ComposeNavigationTheme {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash,
        ) {
            composable<Screen.Splash> {
                SplashScreen(
                    navigateWelcomeScreen = {
                        navController.navigate(Screen.Welcome) {
                            popUpTo(Screen.Splash) {
                                inclusive = true
                            }
                        }
                    },
                    navigateLoginScreen = {
                        navController.navigate(Screen.Login) {
                            popUpTo(Screen.Splash) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<Screen.Welcome>{
                WelcomeScreen()
            }

            composable<Screen.Login>{
                LoginScreen(
                    navigateMainScreen = {
                        navController.navigate(Screen.NavigationDrawer) {
                            popUpTo(Screen.Login) {
                                inclusive = true
                            }
                        }
                    },
                    navigateRegistrationScreen = {
                        navController.navigate(Screen.Registration)
                    }
                )
            }

            composable<Screen.Registration> {
                RegistrationScreen(
                    navigateMainScreen = {
                        navController.navigate(Screen.NavigationDrawer){
                            popUpTo(Screen.Login) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<Screen.NavigationDrawer>{
                NavigationDrawerScreen(
                    navigateCallDetailScreen = {
                        navController.navigate(Screen.CallDetail(it))
                    }
                )
            }

            composable<Screen.CallDetail> { navBackStackEntry ->
                val call: Screen.CallDetail = navBackStackEntry.toRoute()
                DetailCallScreen(call.email)
            }

            composable<Screen.EventDetail> { navBackStackEntry ->
                val event: Screen.EventDetail = navBackStackEntry.toRoute()
                DetailEventScreen(event.event)
            }
        }
    }
}