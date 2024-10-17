package com.mw.example.composenavigation.graph.start

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
internal data object LoginGraph

fun NavGraphBuilder.addLoginGraph(
    navigateWelcomeScreen: () -> Unit = {},
    navigateLoginScreen: () -> Unit = {},
    navigateMainScreen: () -> Unit = {},
    navigateRegistrationScreen: () -> Unit = {}
) {
    navigation<LoginGraph>(startDestination = Splash) {

        splashDestination(
            navigateWelcomeScreen = navigateWelcomeScreen,
            navigateLoginScreen = navigateLoginScreen
        )

        welcomeDestination()

        loginDestination(
            navigateMainScreen = navigateMainScreen,
            navigateRegistrationScreen = navigateRegistrationScreen
        )

        registrationDestination(navigateMainScreen = navigateMainScreen)

    }
}