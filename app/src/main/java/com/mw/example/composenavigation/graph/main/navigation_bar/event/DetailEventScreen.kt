package com.mw.example.composenavigation.graph.main.navigation_bar.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class EventDetail(
    val event: String
)

fun NavGraphBuilder.eventDetailDestination() {
    composable<EventDetail> { navBackStackEntry ->
        val event: EventDetail = navBackStackEntry.toRoute()
        DetailEventScreen(event.event)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventScreen(
    event: String
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "DetailEventScreen")
            }
        )
        Text(
            text = event,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}