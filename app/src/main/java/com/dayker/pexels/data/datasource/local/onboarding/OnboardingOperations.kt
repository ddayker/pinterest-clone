package com.dayker.pexels.data.datasource.local.onboarding

import kotlinx.coroutines.flow.Flow

interface OnboardingOperations {

    suspend fun saveOnboardingState(isCompleted: Boolean)

    fun isOnboardingCompleted(): Flow<Boolean>
}