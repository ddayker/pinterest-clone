package com.dayker.pexels.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.auth.domain.client.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authClient: AuthClient,
) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<AuthScreenAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            AuthScreenEvent.OnSignInClicked -> {
                viewModelScope.launch {
                    val signInIntentSender = authClient.initiateSignIn()
                    _actionFlow.emit(AuthScreenAction.ShowSignInRequest(signInIntentSender))
                }
            }

            is AuthScreenEvent.OnSignInRequest -> {
                viewModelScope.launch {
                    val signInResult = authClient.completeSignIn(
                        intent = event.intent ?: return@launch
                    )
                    if (signInResult.data != null) {
                        _actionFlow.emit(AuthScreenAction.OpenHomeScreen)
                    } else {
                        signInResult.errorMessage?.let { errorMessage ->
                            _actionFlow.emit(AuthScreenAction.ShowErrorMessage(errorMessage))
                        }
                    }
                }
            }
        }
    }
}