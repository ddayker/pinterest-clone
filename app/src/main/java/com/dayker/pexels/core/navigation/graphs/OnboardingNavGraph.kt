package com.dayker.pexels.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dayker.pexels.core.navigation.graphs.OnboardingScreen.ONBOARDING_ROUTE
import com.dayker.pexels.core.navigation.navanimations.scaleInAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleOutAnimation
import com.dayker.pexels.presentation.onboarding.OnboardingScreen

fun NavGraphBuilder.onboardingNavGraph(navController: NavController) {
    navigation(
        route = Graph.ONBOARDING_GRAPH_ROUTE,
        startDestination = ONBOARDING_ROUTE
    ) {
        composable(
            route = ONBOARDING_ROUTE,
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
            OnboardingScreen(navController = navController)
        }
    }
}


object OnboardingScreen {
    const val ONBOARDING_ROUTE = "onboarding_route"
}