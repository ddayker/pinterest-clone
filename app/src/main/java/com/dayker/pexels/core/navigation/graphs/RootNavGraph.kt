package com.dayker.pexels.core.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dayker.pexels.core.navigation.NavigationScreen
import com.dayker.pexels.core.navigation.graphs.Graph.IMAGE_ID_PARAM
import com.dayker.pexels.core.navigation.graphs.Graph.IS_IMAGE_BOOKMARK
import com.dayker.pexels.core.navigation.graphs.Graph.IS_IMAGE_CURATED_PARAM
import com.dayker.pexels.core.navigation.navanimations.scaleInAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleOutAnimation
import com.dayker.pexels.presentation.details.DetailsScreen
import com.dayker.pexels.presentation.onboarding.OnboardingScreen


@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    isOnboardingFinished: Boolean
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if (isOnboardingFinished) Graph.NAVIGATION_BAR_ROUTE else Graph.ONBOARDING_ROUTE
    ) {
        composable(route = Graph.ONBOARDING_ROUTE) {
            OnboardingScreen(navController = navController)
        }
        composable(route = Graph.NAVIGATION_BAR_ROUTE) {
            NavigationScreen(windowSize = windowSize, rootNavController = navController)
        }
        composable(
            route = Graph.DETAILS_ROUTE + "?${IMAGE_ID_PARAM}={$IMAGE_ID_PARAM}&${IS_IMAGE_CURATED_PARAM}={$IS_IMAGE_CURATED_PARAM}&${IS_IMAGE_BOOKMARK}={$IS_IMAGE_BOOKMARK}",
            arguments = listOf(
                navArgument(
                    name = IMAGE_ID_PARAM
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = IS_IMAGE_CURATED_PARAM
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument(
                    name = IS_IMAGE_BOOKMARK
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
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
            DetailsScreen(navController = navController)
        }
    }
}

object Graph {
    const val ONBOARDING_ROUTE = "onboarding_route"

    const val ROOT = "root"
    const val NAVIGATION_BAR_ROUTE = "navigation_route"

    const val DETAILS_ROUTE = "details_route"
    const val IMAGE_ID_PARAM = "movie_id"
    const val IS_IMAGE_CURATED_PARAM = "image_curated"
    const val IS_IMAGE_BOOKMARK = "image_bookmark"
}