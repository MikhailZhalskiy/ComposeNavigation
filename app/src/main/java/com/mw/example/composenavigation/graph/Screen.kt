package com.mw.example.composenavigation.graph

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen {
    protected abstract val route: String
    abstract fun route(): String

    object Splash: Screen() {
        override val route: String = "splash"
        override fun route(): String = route
    }

    object Welcome: Screen() {
        override val route: String = "welcome"
        override fun route(): String = route
    }

    object Login: Screen() {
        override val route: String = "login"
        override fun route(): String = route
    }

    object Registration: Screen() {
        override val route: String = "registration"
        override fun route(): String = route
    }

    object NavigationDrawer: Screen() {
        override val route: String = "drawer"
        override fun route(): String = route
    }

    object NavigationBar: Screen() {
        override val route: String = "bar"
        override fun route(): String = route
    }

    object NavigationTabBar : Screen() {
        override val route: String = "tab"
        override fun route(): String = route
    }

    object NavigationBottomSheet : Screen() {
        override val route: String = "bottom_sheet"
        override fun route(): String = route
    }

    object NavigationBackdrop : Screen() {
        override val route: String = "backdrop"
        override fun route(): String = route
    }

    object NavigationBottomAppBar: Screen() {
        override val route: String = "bottom_app_bar"
        override fun route(): String = route
    }

    object EmailList: Screen() {
        override val route: String = "email_list"
        override fun route(): String = route
    }

    object EmailDetail: Screen() {
        override val route: String = "email_detail"
        override fun route(): String = "$route/{$emailArg}"
        fun createRouteWithArgs(email: String): String = "$route/$email"
        fun arguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(emailArg) { type = NavType.StringType }
            )
        }
        const val emailArg = "email"
    }

    object FavoriteList: Screen() {
        override val route: String = "favorite_list"
        override fun route(): String = route
    }

    object FavoriteDetail: Screen() {
        override val route: String = "favorite_detail"
        override fun route(): String = "$route/{$favoriteArg}"
        fun createRouteWithArgs(favorite: String): String = "$route/$favorite"
        fun arguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(favoriteArg) { type = NavType.StringType }
            )
        }
        const val favoriteArg = "email"
    }

    object CallList: Screen() {
        override val route: String = "call_list"
        override fun route(): String = route
    }

    object CallDetail: Screen() {
        override val route: String = "call_detail"
        override fun route(): String = "$route/{$callArg}"
        fun createRouteWithArgs(call: String): String = "$route/$call"
        fun arguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(callArg) { type = NavType.StringType }
            )
        }
        const val callArg = "email"
    }

    object EventList: Screen() {
        override val route: String = "event_list"
        override fun route(): String = route
    }

    object EventDetail: Screen() {
        override val route: String = "event_detail"
        override fun route(): String = "$route/{$eventArg}"
        fun createRouteWithArgs(event: String): String = "$route/$event"
        fun arguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(eventArg) { type = NavType.StringType }
            )
        }
        const val eventArg = "event"
    }

    object FeatureFavorite: Screen() {
        override val route: String = "nav_favorite_list"
        override fun route(): String = route
    }
}

sealed class Navigation {
    abstract fun route(): String

    object EmailList: Navigation() {
        override fun route(): String = "nav_email_list"
    }
    object FavoriteList: Navigation() {
        override fun route(): String = "nav_favorite_list"
    }
    object CallList: Navigation() {
        override fun route(): String = "nav_call_list"
    }
    object EventList: Navigation() {
        override fun route(): String = "nav_event_list"
    }
}