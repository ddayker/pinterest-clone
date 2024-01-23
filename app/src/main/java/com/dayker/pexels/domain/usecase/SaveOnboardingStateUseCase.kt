package com.dayker.pexels.domain.usecase

import com.dayker.pexels.domain.repository.OnboardingRepository
import javax.inject.Inject

class SaveOnboardingStateUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(isCompleted: Boolean): Result<Unit> =
        repository.saveOnboardingState(isCompleted)
}