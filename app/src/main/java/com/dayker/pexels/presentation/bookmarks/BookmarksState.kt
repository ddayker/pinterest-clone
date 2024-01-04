package com.dayker.pexels.presentation.bookmarks

import com.dayker.pexels.domain.model.Image

data class BookmarksState(

    val isLoading: Boolean = true,

    val images: List<Image> = emptyList(),

    val isNoBookmarks: Boolean = false
)