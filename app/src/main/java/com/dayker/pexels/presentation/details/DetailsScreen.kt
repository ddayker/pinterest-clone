package com.dayker.pexels.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.dayker.pexels.core.util.Container
import com.dayker.pexels.presentation.details.components.DetailsBottomBar
import com.dayker.pexels.presentation.details.components.DetailsTopBar
import com.dayker.pexels.presentation.details.components.NotFoundStub

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Container(viewModel.actionFlow) { action ->
        when (action) {
            DetailsScreenAction.Explore -> {
                navController.popBackStack(
                    navController.graph.findStartDestination().id,
                    inclusive = false
                )
            }

            DetailsScreenAction.GoBack -> {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailsTopBar(
                name = state.photographer,
                backButtonAction = {
                    viewModel.onEvent(DetailsScreenEvent.OnBackClicked)
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(state is DetailsState.ImageDetails) {
                DetailsBottomBar(
                    isBookmark = state.isBookmark,
                    onDownloadClicked = {
                        viewModel.onEvent(DetailsScreenEvent.OnDownloadClicked)
                    },
                    onBookmarkClicked = {
                        viewModel.onEvent(DetailsScreenEvent.OnBookmarkClicked)
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (state) {
                is DetailsState.ImageDetails -> {
                    Box(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = state.src,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))

                        )
                    }
                }

                DetailsState.IsLoading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }

                DetailsState.IsNotFound -> {
                    NotFoundStub {
                        viewModel.onEvent(DetailsScreenEvent.OnExploreClicked)
                    }
                }
            }
        }
    }
}