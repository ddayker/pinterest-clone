package com.dayker.pexels.domain.repository

interface OnboardingRepository {

    suspend fun saveOnboardingState(isCompleted: Boolean): Result<Unit>

    suspend fun isOnboardingCompleted(): Boolean
}