package com.dayker.pexels.core.navigation.navanimations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry


fun AnimatedContentTransitionScope<NavBackStackEntry>.intoRightAnimation() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.intoLeftAnimation() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.outRightAnimation() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.outLeftAnimation() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.scaleInAnimation() =
    scaleIn(
        animationSpec = tween(
            easing = FastOutSlowInEasing
        )
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.scaleOutAnimation() =
    scaleOut(
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )



