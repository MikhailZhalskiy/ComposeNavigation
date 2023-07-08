package com.mw.example.composenavigation.graph.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WelcomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "WelcomeScreen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}