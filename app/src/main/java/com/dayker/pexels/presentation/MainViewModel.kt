package com.dayker.pexels.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.domain.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    private val _onBoardingIsCompleted = MutableStateFlow<Boolean?>(null)
    val onBoardingIsCompleted: StateFlow<Boolean?> = _onBoardingIsCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingIsCompleted.value =
                onboardingRepository.isOnboardingCompleted().stateIn(viewModelScope).value
        }
    }
}