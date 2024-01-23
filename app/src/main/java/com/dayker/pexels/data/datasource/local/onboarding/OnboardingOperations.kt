package com.dayker.pexels.data.datasource.local.onboarding

interface OnboardingOperations {

    suspend fun saveOnboardingState(isCompleted: Boolean)

    suspend fun isOnboardingCompleted(): Boolean
}