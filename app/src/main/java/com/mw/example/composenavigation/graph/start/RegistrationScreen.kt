package com.mw.example.composenavigation.graph.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object Registration

fun NavGraphBuilder.registrationDestination(
    navigateMainScreen: () -> Unit = {},
) {
    composable<Registration> {
        RegistrationScreen(
            navigateMainScreen = navigateMainScreen
        )
    }
}

@Composable
fun RegistrationScreen(
    navigateMainScreen: () -> Unit = {},
) {
    var userName by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Text(
                text = "RegistrationScreen",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Button(
                onClick = { navigateMainScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Зарегестрироваться")
            }
        }
    }
}