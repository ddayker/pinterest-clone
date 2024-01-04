package com.dayker.pexels.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeaturedList(
    modifier: Modifier = Modifier,
    titles: List<String>,
    picked: String,
    onItemClick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier.animateContentSize()
    ) {
        items(titles) { title ->
            FeaturedItem(
                title = title,
                isSelected = title == picked,
                modifier = Modifier
                    .padding(end = 11.dp)
                    .animateItemPlacement()
            ) {
                onItemClick(title)
            }
        }
    }
}