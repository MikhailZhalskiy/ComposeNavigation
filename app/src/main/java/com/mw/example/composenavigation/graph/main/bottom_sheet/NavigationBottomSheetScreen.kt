package com.mw.example.composenavigation.graph.main.bottom_sheet

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.mw.example.composenavigation.graph.main.navigation_bar.DetailEmailScreen
import com.mw.example.composenavigation.graph.main.navigation_bar.EmailListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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