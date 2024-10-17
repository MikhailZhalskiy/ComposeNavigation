package com.mw.example.composenavigation.graph.main.bottom_sheet

import androidx.activity.compose.BackHandler
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mw.example.composenavigation.graph.main.navigation_bar.email.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.email.EmailListScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
internal data object NavigationBottomSheet

fun NavGraphBuilder.navigationBottomSheet() {
    composable<NavigationBottomSheet> {
        NavigationBottomSheetScreen()
    }
}

@Composable
fun NavigationBottomSheetScreen() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var email by rememberSaveable { mutableStateOf("") }

    BackHandler(sheetState.isVisible) {
        scope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            DetailEmailScreen(email = email)
        }
    ) {
        EmailListScreen(
            navigateEmailDetailScreen = {
                email = it
                scope.launch { sheetState.show() }
            }
        )
    }
}