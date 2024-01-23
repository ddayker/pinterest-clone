package com.dayker.pexels.core.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dayker.pexels.core.navigation.NavigationBarScreen
import com.dayker.pexels.core.navigation.navanimations.intoLeftAnimation
import com.dayker.pexels.core.navigation.navanimations.intoRightAnimation
import com.dayker.pexels.core.navigation.navanimations.outLeftAnimation
import com.dayker.pexels.core.navigation.navanimations.outRightAnimation
import com.dayker.pexels.presentation.bookmarks.BookmarksScreen
import com.dayker.pexels.presentation.home.HomeScreen
import com.dayker.pexels.presentation.profile.ProfileScreen

@SuppressLint("RestrictedApi")
@Composable
fun NavigationBarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
    windowSize: WindowSizeClass
) {
    NavHost(
        navController = navController,
        route = Graph.NAVIGATION_BAR_ROUTE,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable(
            route = NavigationBarScreen.Home.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Profile.route -> {
                        intoLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Profile.route -> {
                        outRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            HomeScreen(
                modifier = modifier,
                navController = rootNavController
            )
        }
        composable(
            route = NavigationBarScreen.Favorites.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Home.route -> {
                        intoRightAnimation()
                    }

                    NavigationBarScreen.Profile.route -> {
                        intoLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Home.route -> {
                        outLeftAnimation()
                    }

                    NavigationBarScreen.Profile.route -> {
                        outRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            BookmarksScreen(
                modifier = modifier,
                navController = rootNavController,
            )
        }
        composable(
            route = NavigationBarScreen.Profile.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Home.route -> {
                        intoRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Home.route -> {
                        outLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            ProfileScreen(
                modifier = modifier,
                navController = navController,
                windowSize = windowSize
            )
        }
    }
}