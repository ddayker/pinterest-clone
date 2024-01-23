package com.dayker.pexels.domain.usecase

import com.dayker.pexels.domain.repository.OnboardingRepository
import javax.inject.Inject

class CheckIsOnboardingCompletedUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): Boolean = repository.isOnboardingCompleted()
}