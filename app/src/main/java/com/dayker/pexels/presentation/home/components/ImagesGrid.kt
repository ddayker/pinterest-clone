package com.dayker.pexels.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.dayker.pexels.domain.model.Image

@Composable
fun ImagesGrid(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<Image>,
    imageOnClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 13.dp,
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            content = {
                items(count = images.itemCount) { index ->
                    val item = images[index]
                    if (item != null) {
                        ImageItem(image = item, modifier = Modifier.clickable {
                            imageOnClicked(item.id)
                        })
                    }
                }
            })
    }
}