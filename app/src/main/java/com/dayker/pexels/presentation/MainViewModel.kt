package com.dayker.pexels.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.domain.usecase.CheckIsOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkIsOnboardingCompletedUseCase: CheckIsOnboardingCompletedUseCase
) : ViewModel() {

    private val _onBoardingIsCompleted = MutableStateFlow<Boolean?>(null)
    val onBoardingIsCompleted: StateFlow<Boolean?> = _onBoardingIsCompleted

    init {
        // TODO: check for onboarding state & authentication, return initial screen route
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingIsCompleted.update {
                println(checkIsOnboardingCompletedUseCase())
                checkIsOnboardingCompletedUseCase()
            }
        }
    }
}