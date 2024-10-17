package com.mw.example.composenavigation.graph.main.navigation_bar.email

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
internal data object EmailList

fun NavGraphBuilder.emailListDestination(
    navigateEmailDetailScreen: (String) -> Unit = {},
) {
    composable<EmailList> {
        EmailListScreen(
            navigateEmailDetailScreen = navigateEmailDetailScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailListScreen(
    navigateEmailDetailScreen: (String) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "EmailListScreen")
            }
        )
        val itemList = List(20) {"Email $it"}
        LazyColumn() {
            items(itemList) { item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateEmailDetailScreen(item) }
                        .padding(16.dp),
                    text = item
                )
            }
        }
    }
}