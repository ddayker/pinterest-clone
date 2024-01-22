package com.dayker.pexels.auth.domain.model

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)