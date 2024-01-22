package com.dayker.pexels.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dayker.pexels.auth.presentation.AuthenticationScreen
import com.dayker.pexels.core.navigation.graphs.Graph.AUTH_GRAPH_ROUTE
import com.dayker.pexels.core.navigation.navanimations.scaleInAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleOutAnimation

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = AUTH_GRAPH_ROUTE,
        startDestination = AuthScreen.Authentication.route
    ) {
        composable(
            route = AuthScreen.Authentication.route,
            enterTransition = {
                scaleInAnimation()
            },
            popEnterTransition = {
                scaleInAnimation()
            },
            exitTransition = {
                scaleOutAnimation()
            },
            popExitTransition = {
                scaleOutAnimation()
            }
        ) {
            AuthenticationScreen(navController = navController)
        }
    }
}


sealed class AuthScreen(
    val route: String
) {
    object Authentication : AuthScreen(route = "auth_route")
}