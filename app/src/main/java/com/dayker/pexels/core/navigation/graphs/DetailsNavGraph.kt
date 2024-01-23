package com.dayker.pexels.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dayker.pexels.core.navigation.graphs.DetailsScreen.DETAILS_ROUTE
import com.dayker.pexels.core.navigation.graphs.DetailsScreen.IMAGE_ID_PARAM
import com.dayker.pexels.core.navigation.graphs.DetailsScreen.IS_IMAGE_BOOKMARK
import com.dayker.pexels.core.navigation.graphs.DetailsScreen.IS_IMAGE_CURATED_PARAM
import com.dayker.pexels.core.navigation.navanimations.scaleInAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleOutAnimation
import com.dayker.pexels.presentation.details.DetailsScreen

fun NavGraphBuilder.detailsNavGraph(navController: NavController) {
    navigation(
        route = Graph.DETAILS_GRAPH_ROUTE,
        startDestination = DETAILS_ROUTE
    ) {
        composable(
            route = DETAILS_ROUTE + "?${IMAGE_ID_PARAM}={${IMAGE_ID_PARAM}}&${IS_IMAGE_CURATED_PARAM}={${IS_IMAGE_CURATED_PARAM}}&${IS_IMAGE_BOOKMARK}={${IS_IMAGE_BOOKMARK}}",
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


object DetailsScreen {
    const val DETAILS_ROUTE = "details_route"
    const val IMAGE_ID_PARAM = "movie_id"
    const val IS_IMAGE_CURATED_PARAM = "image_curated"
    const val IS_IMAGE_BOOKMARK = "image_bookmark"
}
