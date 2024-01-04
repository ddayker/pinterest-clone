package com.dayker.pexels.presentation.details


sealed class DetailsScreenAction {

    object GoBack : DetailsScreenAction()

    object Explore : DetailsScreenAction()
}