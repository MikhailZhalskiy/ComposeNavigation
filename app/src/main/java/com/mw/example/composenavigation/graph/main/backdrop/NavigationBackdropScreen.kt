package com.mw.example.composenavigation.graph.main.backdrop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mw.example.composenavigation.graph.EventBusNavigation
import com.mw.example.composenavigation.graph.Navigation
import com.mw.example.composenavigation.graph.NavigationEvent
import com.mw.example.composenavigation.graph.main.navigation_bar.addCallGraph
import com.mw.example.composenavigation.graph.main.navigation_bar.addEmailGraph
import com.mw.example.composenavigation.graph.main.navigation_bar.addEventGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavigationBackdropScreen(
    navigateCallDetailScreen: (String) -> Unit = {},
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val items = listOf(
        BackdropItem("Email", Icons.Filled.Email, Navigation.EmailList),
        BackdropItem("Call", Icons.Filled.Call, Navigation.CallList),
        BackdropItem("Event", Icons.Filled.Share, Navigation.EventList),
    )

    BackdropScaffold(
        scaffoldState = scaffoldState,
        backLayerBackgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
        backLayerContentColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
        appBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
                ),
                title = {
                    Text(
                        "Backdrop scaffold",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Localized description")
                        }
                    } else {
                        IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Localized description")
                        }
                    }
                },
                actions = {
                    var clickCount by remember { mutableStateOf(0) }
                    IconButton(
                        onClick = {
                            // show snackbar as a suspend function
                            scope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar("Snackbar #${++clickCount}")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Localized description")
                    }
                },
            )
        },
        backLayerContent = {
            Column {
                items.forEachIndexed { index, backdropItem ->
                    NavigationBackdropItem(
                        label = {
                            Text(text = backdropItem.label)
                        },
                        selected = selectedIndex == index,
                        onClick = {
                            scope.launch { scaffoldState.conceal() }
                            navController.navigate(backdropItem.screen.route()) {
                                popUpTo(items[selectedIndex].screen.route()) {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                                selectedIndex = index
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        },
        frontLayerContent = {
            NavHost(
                navController = navController,
                startDestination = items[0].screen.route()
            ) {
                addEmailGraph(navController)
                addCallGraph(navigateCallDetailScreen)
                addEventGraph {
                    scope.launch {
                        EventBusNavigation.send(NavigationEvent.Event(it))
                    }
                }
            }
        }
    )
}

data class BackdropItem(
    val label: String,
    val icon: ImageVector,
    val screen: Navigation
)
