package com.mw.example.composenavigation.graph.main.navigation_bar.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object FavoriteList

fun NavGraphBuilder.favoriteListDestination(
    navigateFavoriteDetailScreen: (String) -> Unit = {},
) {
    composable<FavoriteList> {
        FavoriteListScreen(
            navigateFavoriteDetailScreen = navigateFavoriteDetailScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    navigateFavoriteDetailScreen: (String) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "FavoriteListScreen")
            }
        )
        val itemList = List(20) {"Favorite $it"}
        LazyColumn() {
            items(itemList) { item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateFavoriteDetailScreen(item) }
                        .padding(16.dp),
                    text = item
                )
            }
        }
    }
}