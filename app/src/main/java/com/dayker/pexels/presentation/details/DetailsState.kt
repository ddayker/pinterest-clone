package com.dayker.pexels.presentation.details

sealed class DetailsState(
    val photographer: String = "",
    val src: String = "",
    val isBookmark: Boolean = false
) {
    object IsNotFound : DetailsState()

    object IsLoading : DetailsState()

    class ImageDetails(photographer: String, src: String, isBookmark: Boolean) :
        DetailsState(photographer, src, isBookmark)
}