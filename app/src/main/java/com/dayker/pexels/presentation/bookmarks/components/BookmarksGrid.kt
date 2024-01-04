package com.dayker.pexels.presentation.bookmarks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayker.pexels.domain.model.Image

@Composable
fun BookmarksGrid(
    modifier: Modifier = Modifier,
    images: List<Image>,
    imageOnClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 13.dp,
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            content = {
                items(images) { item ->
                    BookmarksImageItem(image = item, modifier = Modifier.clickable {
                        imageOnClicked(item.id)
                    })
                }
            })
    }
}