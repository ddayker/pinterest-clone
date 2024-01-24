package com.dayker.pexels.core.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dayker.pexels.core.navigation.components.AppBottomBar
import com.dayker.pexels.core.navigation.components.AppNavigationRail
import com.dayker.pexels.core.navigation.graphs.NavigationBarScreen
import com.dayker.pexels.core.navigation.graphs.RootNavigationGraph

@Composable
fun ComposeApplication(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    startDestinationRoute: String
) {
    val screens = NavigationBarScreen.getAllNavigationScreens()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            AppNavigationPortrait(
                navController,
                currentDestination,
                windowSize,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                },
                startDestinationRoute
            )
        }

        else -> {
            AppNavigationLandscape(
                navController,
                currentDestination,
                windowSize,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                },
                startDestinationRoute
            )
        }
    }
}

fun elementOnClick(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

@Composable
fun AppNavigationLandscape(
    navController: NavHostController,
    currentDestination: NavDestination?,
    windowSize: WindowSizeClass,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit,
    startDestinationRoute: String
) {
    Row {
        val bottomBarDestination = screens.any { it.route == currentDestination?.route }
        if (bottomBarDestination) {
            AppNavigationRail(
                currentDestination = currentDestination,
                screens = screens,
                elementOnClick = elementOnClick
            )
        }
        RootNavigationGraph(
            navController = navController,
            windowSize = windowSize,
            startDestinationRoute = startDestinationRoute
        )
    }
}

@Composable
fun AppNavigationPortrait(
    navController: NavHostController,
    currentDestination: NavDestination?,
    windowSize: WindowSizeClass,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit,
    startDestinationRoute: String
) {
    Scaffold(
        bottomBar = {
            val bottomBarDestination = screens.any { it.route == currentDestination?.route }
            if (bottomBarDestination) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    screens = screens,
                    elementOnClick = elementOnClick
                )
            }
        }
    ) {
        RootNavigationGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            windowSize = windowSize,
            startDestinationRoute = startDestinationRoute
        )
    }
}

