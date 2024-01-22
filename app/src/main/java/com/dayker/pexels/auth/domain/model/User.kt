package com.dayker.pexels.auth.domain.model

data class User(
    val userId: String,
    val email: String?,
    val username: String?,
    val profilePictureUrl: String?
)