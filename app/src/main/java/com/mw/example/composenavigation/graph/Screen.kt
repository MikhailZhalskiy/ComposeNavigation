package com.mw.example.composenavigation.graph

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Splash: Screen()

    @Serializable
    data object Welcome: Screen()

    @Serializable
    data object Login: Screen()

    @Serializable
    data object Registration: Screen()

    @Serializable
    data object NavigationDrawer: Screen()

    @Serializable
    data object NavigationBar: Screen()

    @Serializable
    data object NavigationTabBar : Screen()

    @Serializable
    data object NavigationBottomSheet : Screen()

    @Serializable
    data object NavigationBackdrop : Screen()

    @Serializable
    data object NavigationBottomAppBar: Screen()

    @Serializable
    data object EmailList: Screen()

    @Serializable
    data class EmailDetail(
        val email: String
    ): Screen()

    @Serializable
    data object FavoriteList: Screen()

    @Serializable
    data class FavoriteDetail(
        val email: String
    ): Screen()

    @Serializable
    data object CallList: Screen()

    @Serializable
    data class CallDetail(
        val email: String
    ): Screen()

    @Serializable
    data object EventList: Screen()

    @Serializable
    data class EventDetail(
        val event: String
    ): Screen()

    @Serializable
    data object FeatureFavorite: Screen()
}

sealed class Navigation {

    @Serializable
    data object EmailList: Navigation()

    @Serializable
    data object FavoriteList: Navigation()

    @Serializable
    data object CallList: Navigation()

    @Serializable
    data object EventList: Navigation()
}