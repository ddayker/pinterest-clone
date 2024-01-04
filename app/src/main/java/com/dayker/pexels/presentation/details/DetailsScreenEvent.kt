package com.dayker.pexels.presentation.details


sealed class DetailsScreenEvent {

    object OnBackClicked : DetailsScreenEvent()

    object OnDownloadClicked : DetailsScreenEvent()

    object OnBookmarkClicked : DetailsScreenEvent()

    object OnExploreClicked : DetailsScreenEvent()

}