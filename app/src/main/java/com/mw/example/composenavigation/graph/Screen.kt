package com.mw.example.composenavigation.graph

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

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