package com.dayker.pexels.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.domain.usecase.SaveOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingStateUseCase: SaveOnboardingStateUseCase
) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<OnboardingAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnClickedBackward -> {
                viewModelScope.launch {
                    _actionFlow.emit(OnboardingAction.ScrollBackward)
                }
            }

            OnboardingEvent.OnClickedForward -> {
                viewModelScope.launch {
                    _actionFlow.emit(OnboardingAction.ScrollForward)
                }
            }

            OnboardingEvent.OnClickedGetStarted -> {
                viewModelScope.launch {
                    saveOnboardingStateUseCase(isCompleted = true)
                        .onSuccess {
                            _actionFlow.emit(OnboardingAction.GetStarted)
                        }
                        .onFailure {
                            saveOnboardingStateUseCase(isCompleted = true)
                        }
                }
            }
        }
    }

    companion object {
        const val PAGER_SIZE = 3
    }
}