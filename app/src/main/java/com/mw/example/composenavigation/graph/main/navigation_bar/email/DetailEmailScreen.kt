package com.mw.example.composenavigation.graph.main.navigation_bar.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
internal data class EmailDetail(
    val email: String
)

fun NavGraphBuilder.emailDetailDestination() {
    composable<EmailDetail> { navBackStackEntry ->
        val email: EmailDetail = navBackStackEntry.toRoute()
        DetailEmailScreen(email.email)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEmailScreen(
    email: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = "DetailEmailScreen")
            }
        )
        Text(
            text = email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}