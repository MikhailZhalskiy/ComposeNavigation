package com.mw.example.composenavigation.graph.main.navigation_bar.call

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallListScreen(
    navigateCallDetailScreen: (String) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "CallListScreen")
            }
        )
        val itemList = List(20) {"Call $it"}
        LazyColumn() {
            items(itemList) { item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateCallDetailScreen(item) }
                        .padding(16.dp),
                    text = item
                )
            }
        }
    }
}