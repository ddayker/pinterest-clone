package com.dayker.pexels.core.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.dayker.pexels.core.navigation.NavigationBarScreen

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    val isDestination = screens.any { it.route == currentDestination?.route }
    AnimatedVisibility(
        visible = isDestination
    ) {
        Surface(
            shadowElevation = 25.dp,
        ) {
            NavigationRail(
                modifier = modifier.width(120.dp),
                windowInsets = NavigationRailDefaults.windowInsets,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        elementOnClick = elementOnClick
                    )
                }
            }
        }
    }
}


@Composable
fun ColumnScope.AddItem(
    screen: NavigationBarScreen,
    currentDestination: NavDestination?,
    elementOnClick: (String) -> Unit
) {
    val isActive = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    NavigationRailItem(
        icon = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.9f), contentAlignment = Alignment.Center
                ) {
                    FlipIcon(
                        isActive = isActive,
                        modifier = Modifier.size(24.dp),
                        activeIcon = painterResource(screen.activeIcon),
                        inactiveIcon = painterResource(screen.inactiveIcon),
                    )
                }
                AnimatedVisibility(isActive) {
                    Divider(
                        color = MaterialTheme.colorScheme.primary, modifier = Modifier
                            .width(2.dp)
                            .height(24.dp)
                    )
                }
            }
        },
        selected = isActive,
        alwaysShowLabel = false,
        onClick = {
            if (!isActive) {
                elementOnClick(screen.route)
            }
        },
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
            indicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
    )
}