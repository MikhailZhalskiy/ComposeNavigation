package com.mw.example.composenavigation.graph.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
internal data object Splash

fun NavGraphBuilder.splashDestination(
    navigateWelcomeScreen: () -> Unit = {},
    navigateLoginScreen: () -> Unit = {},
) {
    composable<Splash> {
        SplashScreen(
            navigateWelcomeScreen = navigateWelcomeScreen,
            navigateLoginScreen = navigateLoginScreen
        )
    }
}

@Composable
fun SplashScreen(
    navigateWelcomeScreen: () -> Unit = {},
    navigateLoginScreen: () -> Unit = {}
) {
    var timer by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "SplashScreen",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )

            Text(
                text = timer,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        val time = 1
        repeat(time) {
            timer = "${ time-it }"
            delay(1000)
        }
//        if (Random.nextBoolean()) {
//            navigateWelcomeScreen()
//        } else {
//            navigateLoginScreen()
//        }
        navigateLoginScreen()
    }
}