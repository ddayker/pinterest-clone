package com.dayker.pexels.presentation.home

sealed class HomeScreenEvent {

    data class OnSearch(val query: String) : HomeScreenEvent()

    data class OnTitleClicked(val title: String) : HomeScreenEvent()

    object OnExploreClicked : HomeScreenEvent()

    object OnReloadClicked : HomeScreenEvent()

    data class OnImageClicked(val id: Int) : HomeScreenEvent()

}