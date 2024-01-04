package com.dayker.pexels.presentation.home

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dayker.pexels.core.components.Container
import com.dayker.pexels.domain.model.Image
import com.dayker.pexels.presentation.home.components.FeaturedList
import com.dayker.pexels.presentation.home.components.ImagesGrid
import com.dayker.pexels.presentation.home.components.NetworkStub
import com.dayker.pexels.presentation.home.components.NoResultsStub
import com.dayker.pexels.presentation.home.components.SearchingBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val images: LazyPagingItems<Image> = state.images.collectAsLazyPagingItems()

    Container(viewModel.actionFlow) { action ->
        when (action) {
            is HomeScreenAction.OpenImageDetails -> {
                println(action.id)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchingBar(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 12.dp)
                    .fillMaxWidth(),
                onValueChange = { value ->
                    viewModel.onEvent(HomeScreenEvent.OnSearch(value))
                },
                query = state.query
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            if (state.featuredCollections.isNotEmpty()) {
                FeaturedList(
                    titles = state.featuredCollections,
                    picked = state.query,
                    modifier = Modifier.padding(start = 24.dp),
                    onItemClick = { title ->
                        viewModel.onEvent(HomeScreenEvent.OnTitleClicked(title))
                    }
                )
            }
            if (images.itemSnapshotList.isNotEmpty()) {
                ImagesGrid(
                    images = images,
                    imageOnClicked = { id ->
                        viewModel.onEvent(HomeScreenEvent.OnImageClicked(id))
                    },
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
                )
            } else {
                when (images.loadState.refresh) {
                    is LoadState.Loading -> {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }

                    is LoadState.Error -> {
                        NetworkStub {
                            viewModel.onEvent(HomeScreenEvent.OnReloadClicked)
                        }
                    }

                    else -> {
                        if (images.loadState.source.refresh is LoadState.NotLoading) {
                            NoResultsStub {
                                viewModel.onEvent(HomeScreenEvent.OnExploreClicked)
                            }
                        }
                    }
                }
            }
        }
    }
}