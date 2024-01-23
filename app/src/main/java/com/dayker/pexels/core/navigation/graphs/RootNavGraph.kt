package com.dayker.pexels.core.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dayker.pexels.core.navigation.graphs.Graph.AUTH_GRAPH_ROUTE

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = AUTH_GRAPH_ROUTE
    ) {
        mainNavGraph(windowSize = windowSize, navController = navController, modifier = modifier)
        detailsNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}

object Graph {

    const val ROOT = "root"
    const val AUTH_GRAPH_ROUTE = "auth_graph"
    const val DETAILS_GRAPH_ROUTE = "details_graph"
    const val MAIN_GRAPH_ROUTE = "main_graph"

}