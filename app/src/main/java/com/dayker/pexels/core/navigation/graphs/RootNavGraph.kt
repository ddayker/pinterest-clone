package com.dayker.pexels.core.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dayker.pexels.core.navigation.NavigationScreen


@Composable
fun RootNavigationGraph(navController: NavHostController, windowSize: WindowSizeClass) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.NAVIGATION_BAR_ROUTE
    ) {
        composable(route = Graph.NAVIGATION_BAR_ROUTE) {
            NavigationScreen(windowSize = windowSize)
        }
        composable(route = Graph.DETAILS_ROUTE) {

        }
    }
}

object Graph {
    const val ROOT = "root"
    const val DETAILS_ROUTE = "details_route"
    const val NAVIGATION_BAR_ROUTE = "navigation_route"
}