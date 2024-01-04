package com.dayker.pexels.presentation.bookmarks

sealed class BookmarksScreenEvent {

    object OnExploreClicked : BookmarksScreenEvent()

    data class OnImageClicked(val id: Int) : BookmarksScreenEvent()

}