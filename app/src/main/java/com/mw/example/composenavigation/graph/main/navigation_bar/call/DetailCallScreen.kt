package com.mw.example.composenavigation.graph.main.navigation_bar.call

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCallScreen(
    call: String
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "DetailCallScreen")
            }
        )
        Text(
            text = call,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}