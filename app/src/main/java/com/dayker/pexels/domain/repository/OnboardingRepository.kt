package com.dayker.pexels.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {

    suspend fun saveOnboardingState(isCompleted: Boolean)

    fun isOnboardingCompleted(): Flow<Boolean>
}