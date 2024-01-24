package com.dayker.pexels.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.core.navigation.graphs.Graph.AUTH_GRAPH_ROUTE
import com.dayker.pexels.core.navigation.graphs.Graph.MAIN_GRAPH_ROUTE
import com.dayker.pexels.core.navigation.graphs.Graph.ONBOARDING_GRAPH_ROUTE
import com.dayker.pexels.domain.usecase.CheckIsOnboardingCompletedUseCase
import com.dayker.pexels.domain.usecase.CheckIsUserAuthenticatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkIsOnboardingCompletedUseCase: CheckIsOnboardingCompletedUseCase,
    private val checkIsUserAuthenticatedUseCase: CheckIsUserAuthenticatedUseCase
) : ViewModel() {

    private val _startDestinationRoute = MutableStateFlow<String?>(null)
    val startDestinationRoute: StateFlow<String?> = _startDestinationRoute

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkIsUserAuthenticatedUseCase()) {
                _startDestinationRoute.emit(MAIN_GRAPH_ROUTE)
            } else {
                if (checkIsOnboardingCompletedUseCase()) {
                    _startDestinationRoute.emit(AUTH_GRAPH_ROUTE)
                } else {
                    _startDestinationRoute.emit(ONBOARDING_GRAPH_ROUTE)
                }
            }
        }
    }
}