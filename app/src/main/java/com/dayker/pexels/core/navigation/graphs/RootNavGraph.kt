package com.dayker.pexels.core.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier,
    startDestinationRoute: String
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestinationRoute
    ) {
        mainNavGraph(windowSize = windowSize, navController = navController, modifier = modifier)
        authNavGraph(navController = navController)
        onboardingNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root"
    const val AUTH_GRAPH_ROUTE = "auth_graph"
    const val MAIN_GRAPH_ROUTE = "main_graph"
    const val ONBOARDING_GRAPH_ROUTE = "onboarding_graph"
}