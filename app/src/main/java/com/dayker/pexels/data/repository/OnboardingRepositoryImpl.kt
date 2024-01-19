package com.dayker.pexels.data.repository

import com.dayker.pexels.data.datasource.local.onboarding.OnboardingOperations
import com.dayker.pexels.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingOperations: OnboardingOperations
) : OnboardingRepository {

    override suspend fun saveOnboardingState(isCompleted: Boolean) =
        onboardingOperations.saveOnboardingState(isCompleted)

    override fun isOnboardingCompleted(): Flow<Boolean> =
        onboardingOperations.isOnboardingCompleted()
}