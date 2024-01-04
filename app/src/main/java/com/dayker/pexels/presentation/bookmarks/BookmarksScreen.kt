package com.dayker.pexels.presentation.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.dayker.pexels.core.navigation.graphs.Graph
import com.dayker.pexels.core.util.Container
import com.dayker.pexels.presentation.bookmarks.components.BookmarksGrid
import com.dayker.pexels.presentation.bookmarks.components.BookmarksTopBar
import com.dayker.pexels.presentation.bookmarks.components.NoBookmarksStub

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Container(viewModel.actionFlow) { action ->
        when (action) {
            is BookmarksScreenAction.OpenImageDetails -> {
                val routeWithParams =
                    "${Graph.DETAILS_ROUTE}?${Graph.IMAGE_ID_PARAM}=${action.id}&${Graph.IS_IMAGE_BOOKMARK}=${true}"
                navController.navigate(route = routeWithParams) {
                    launchSingleTop = true
                }
            }

            BookmarksScreenAction.Explore -> {
                navController.navigate(navController.graph.findStartDestination().id)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BookmarksTopBar()
        }
    ) { padding ->
        Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }
            if (state.isNoBookmarks) {
                NoBookmarksStub {
                    viewModel.onEvent(BookmarksScreenEvent.OnExploreClicked)
                }
            } else {
                BookmarksGrid(
                    images = state.images,
                    imageOnClicked = { id ->
                        viewModel.onEvent(BookmarksScreenEvent.OnImageClicked(id))
                    },
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 38.dp)
                )
            }
        }
    }
}