package com.dayker.pexels.presentation.profile

data class ProfileState(
    val isLoading: Boolean = true,
    val email: String = "",
    val name: String = "",
    val pictureUrl: String = ""
)