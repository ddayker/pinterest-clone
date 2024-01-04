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
import androidx.navigation.compose.rememberNavController
import com.dayker.pexels.core.navigation.components.AppBottomBar
import com.dayker.pexels.core.navigation.components.AppNavigationRail
import com.dayker.pexels.core.navigation.graphs.NavigationBarNavGraph

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController(),
    rootNavController: NavHostController,
    windowSize: WindowSizeClass
) {
    val screens = listOf(
        NavigationBarScreen.Home,
        NavigationBarScreen.Favorites,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            AppNavigationPortrait(
                navController,
                rootNavController,
                currentDestination,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                }
            )
        }

        else -> {
            AppNavigationLandscape(
                navController,
                rootNavController,
                currentDestination,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                }
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
    rootNavController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    Row {
        AppNavigationRail(
            currentDestination = currentDestination,
            screens = screens,
            elementOnClick = elementOnClick
        )
        NavigationBarNavGraph(navController = navController, rootNavController = rootNavController)
    }
}

@Composable
fun AppNavigationPortrait(
    navController: NavHostController,
    rootNavController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                screens = screens,
                elementOnClick = elementOnClick
            )
        }
    ) {
        NavigationBarNavGraph(
            navController = navController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        )
    }
}

