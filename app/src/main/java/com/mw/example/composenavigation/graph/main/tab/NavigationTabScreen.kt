package com.mw.example.composenavigation.graph.main.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mw.example.composenavigation.graph.main.navigation_bar.call.CallListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailListScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.event.EventListScreen
import com.mw.example.mylibrary.favorite.FeatureFavoriteListScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
internal data object NavigationTabBar

fun NavGraphBuilder.navigationTabBar(
    navigateEmailDetailScreen: (String) -> Unit = {},
    navigateCallDetailScreen: (String) -> Unit = {},
    navigateEventDetailScreen: (String) -> Unit = {},
    menuActionButtonClick: () -> Unit = {}
) {
    composable<NavigationTabBar> {
        NavigationTabScreen(
            navigateEmailDetailScreen = navigateEmailDetailScreen,
            navigateCallDetailScreen = navigateCallDetailScreen,
            navigateEventDetailScreen = navigateEventDetailScreen,
            menuActionButtonClick = menuActionButtonClick
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavigationTabScreen(
    navigateEmailDetailScreen: (String) -> Unit = {},
    navigateCallDetailScreen: (String) -> Unit = {},
    navigateEventDetailScreen: (String) -> Unit = {},
    menuActionButtonClick: () -> Unit = {}
) {
    val items = listOf(
        TabItem("Email", Icons.Filled.Email) {
            EmailListScreen(navigateEmailDetailScreen = navigateEmailDetailScreen)
        },
        TabItem("Favorite", Icons.Filled.Favorite) { FeatureFavoriteListScreen() },
        TabItem("Call", Icons.Filled.Call) {
            CallListScreen(navigateCallDetailScreen = navigateCallDetailScreen)
        },
        TabItem("Event", Icons.Filled.Share) {
            EventListScreen(navigateEventDetailScreen = navigateEventDetailScreen)
        }
    )

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "ScrollableTabRow") },
                    navigationIcon = {
                        IconButton(onClick = { menuActionButtonClick() }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.pagerTabIndicatorOffset(
                                pagerState = pagerState,
                                tabPositions = tabPositions
                            )
                        )
                    }
                ) {
                    items.forEachIndexed { index, item ->
                        Tab(
                            selected = index == pagerState.currentPage,
                            onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                Text(text = item.label)
                            },
                            unselectedContentColor = Color.DarkGray
                        )
                    }
                }
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            count = items.size,
            state = pagerState
        ) { page ->
            items[page].screen()
        }
    }
}

data class TabItem(
    val label: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit //Screen
)

@ExperimentalPagerApi
fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    pageIndexMapping: (Int) -> Int = { it },
): Modifier = layout { measurable, constraints ->
    if (tabPositions.isEmpty()) {
        // If there are no pages, nothing to show
        layout(constraints.maxWidth, 0) {}
    } else {
        val currentPage = minOf(tabPositions.lastIndex, pageIndexMapping(pagerState.currentPage))
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffset
        val indicatorWidth = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.width, nextTab.width, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.width, previousTab.width, -fraction).roundToPx()
        } else {
            currentTab.width.roundToPx()
        }
        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).roundToPx()
        } else {
            currentTab.left.roundToPx()
        }
        val placeable = measurable.measure(
            Constraints(
                minWidth = indicatorWidth,
                maxWidth = indicatorWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )
        layout(constraints.maxWidth, maxOf(placeable.height, constraints.minHeight)) {
            placeable.placeRelative(
                indicatorOffset,
                maxOf(constraints.minHeight - placeable.height, 0)
            )
        }
    }
}