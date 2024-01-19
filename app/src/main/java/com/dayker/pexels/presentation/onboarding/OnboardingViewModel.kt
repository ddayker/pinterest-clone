package com.dayker.pexels.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.domain.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<OnboardingAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    private var onboardingJob: Job? = null

    init {
        onboardingJob?.cancel()
        onboardingJob = viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.isOnboardingCompleted()
                .onEach { isOnboardingCompleted ->
                    if (isOnboardingCompleted) {
                        _actionFlow.emit(OnboardingAction.GetStarted)
                    }
                }
                .stateIn(viewModelScope)
        }
    }

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
                    onboardingRepository.saveOnboardingState(isCompleted = true)
                }
            }
        }
    }

    companion object {
        const val PAGER_SIZE = 3
    }
}