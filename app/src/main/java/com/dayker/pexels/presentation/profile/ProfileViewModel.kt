package com.dayker.pexels.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayker.pexels.auth.domain.client.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authClient: AuthClient
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _actionFlow = MutableSharedFlow<ProfileScreenAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            val user = authClient.getSignedInUser()
            user?.let { userInfo ->
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        email = userInfo.email ?: "",
                        name = userInfo.username ?: "",
                        pictureUrl = userInfo.profilePictureUrl ?: ""

                    )
                }
            }
        }
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.OnLogOutClicked -> {
                viewModelScope.launch {
                    authClient.signOut()
                    _actionFlow.emit(ProfileScreenAction.LogOut)
                }
            }
        }
    }
}
