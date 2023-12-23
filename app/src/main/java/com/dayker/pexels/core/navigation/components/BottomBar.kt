package com.dayker.pexels.core.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.dayker.pexels.core.components.FlipIcon
import com.dayker.pexels.core.navigation.NavigationBarScreen


@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    var visible by remember {
        mutableStateOf(true)
    }
    visible = screens.any { it.route == currentDestination?.route }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Surface(
            shadowElevation = 15.dp,
        ) {
            NavigationBar(
                modifier = modifier,
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = MaterialTheme.colorScheme.background,
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
fun RowScope.AddItem(
    screen: NavigationBarScreen,
    currentDestination: NavDestination?,
    elementOnClick: (String) -> Unit
) {
    val isActive = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    NavigationBarItem(
        icon = {
            Column(modifier = Modifier.fillMaxHeight()) {
                AnimatedVisibility(isActive) {
                    Divider(
                        color = MaterialTheme.colorScheme.primary, modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
                ) {
                    FlipIcon(
                        isActive = isActive,
                        modifier = Modifier.size(24.dp),
                        activeIcon = painterResource(screen.activeIcon),
                        inactiveIcon = painterResource(screen.inactiveIcon),
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
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
            indicatorColor = MaterialTheme.colorScheme.background,
        )
    )
}