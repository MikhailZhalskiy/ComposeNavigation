package com.mw.example.mylibrary.favorite

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
fun FeatureDetailFavoriteScreen(
    favorite: String
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "FeatureDetailFavoriteScreen")
            }
        )
        Text(
            text = favorite,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}