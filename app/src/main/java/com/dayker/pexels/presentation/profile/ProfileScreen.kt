package com.dayker.pexels.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dayker.pexels.core.navigation.graphs.Graph
import com.dayker.pexels.core.util.Container
import com.dayker.pexels.presentation.profile.components.LogOutFab
import com.dayker.pexels.presentation.profile.components.ProfileInfo
import com.dayker.pexels.presentation.profile.components.ProfileTopBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    windowSize: WindowSizeClass
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Container(viewModel.actionFlow) { action ->
        when (action) {
            ProfileScreenAction.LogOut -> {
                navController.navigate(route = Graph.AUTH_GRAPH_ROUTE) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            ProfileTopBar()
        },
        floatingActionButton = {
            LogOutFab {
                viewModel.onEvent(ProfileScreenEvent.OnLogOutClicked)
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding()),
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            ProfileInfo(
                windowSize = windowSize,
                imageUrl = state.pictureUrl,
                name = state.name,
                email = state.email,
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
            )
        }
    }
}