package com.dayker.pexels.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dayker.pexels.R

sealed class NavigationBarScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val inactiveIcon: Int,
    @DrawableRes val activeIcon: Int,
) {
    object Home : NavigationBarScreen(
        route = "home",
        title = R.string.home,
        activeIcon = R.drawable.picked_home,
        inactiveIcon = R.drawable.home,
    )

    object Favorites : NavigationBarScreen(
        route = "favorites",
        title = R.string.favorites,
        activeIcon = R.drawable.picked_favorites,
        inactiveIcon = R.drawable.favorites,
    )

    object Profile : NavigationBarScreen(
        route = "profile",
        title = R.string.profile,
        activeIcon = R.drawable.profile_icon,
        inactiveIcon = R.drawable.person_icon_outlined,
    )
}
