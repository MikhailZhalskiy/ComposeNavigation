package com.mw.example.mylibrary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mw.example.mylibrary.favorite.FeatureDetailFavoriteScreen
import com.mw.example.mylibrary.favorite.FeatureFavoriteListScreen

class FavoriteFeatureApi {
    init {
        println("FavoriteFeatureApi()")
    }

    fun getFlowScreen(): @Composable () -> Unit {
        return { FlowScreenFavorite() }
    }

    fun addFeatureFavoriteGraph(navGraphBuilder: NavGraphBuilder, navController: NavHostController) { navGraphBuilder.addFavoriteGraph(navController) }

    fun getFeatureDestination(): FeatureDestination {
        return object : FeatureDestination {
            override val route: String
                get() = "feature_favorite"

            override fun composableFeatureDestination(
                navGraphBuilder: NavGraphBuilder,
                navController: NavHostController
            ) {
                navGraphBuilder.addFavoriteGraph(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowScreenFavorite() {
    val navController = rememberNavController()
    Scaffold() {
        NavHost(navController = navController, startDestination = Navigation.FavoriteList.route(), modifier = Modifier.padding(it)) {
            addFavoriteGraph(navController)
        }
    }
}

fun NavGraphBuilder.addFavoriteGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.FavoriteList.route(),
        route = Navigation.FavoriteList.route()
    ) {
        composable(route = Screen.FavoriteList.route()) {
            FeatureFavoriteListScreen(
                navigateFavoriteDetailScreen = {
                    navController.navigate(Screen.FavoriteDetail.createRouteWithArgs(it))
                }
            )
        }

        composable(
            route = Screen.FavoriteDetail.route(),
            arguments = Screen.FavoriteDetail.arguments()
        ) { navBackStackEntry ->
            val favorite =
                navBackStackEntry.arguments?.getString(Screen.FavoriteDetail.favoriteArg).orEmpty()
            FeatureDetailFavoriteScreen(favorite)
        }
    }
}

sealed class Screen {
    protected abstract val route: String
    abstract fun route(): String

    object FavoriteList: Screen() {
        override val route: String = "feature_favorite"//"favorite_list"
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
}

sealed class Navigation {
    abstract fun route(): String
    object FavoriteList: Navigation() {
        override fun route(): String = "nav_favorite_list"
    }
}

interface FeatureDestination {
    val route: String

    fun composableFeatureDestination(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}