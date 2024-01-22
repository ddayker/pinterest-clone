package com.dayker.pexels.auth.presentation

import android.content.Intent

sealed class AuthScreenEvent {

    object OnSignInClicked : AuthScreenEvent()

    data class OnSignInRequest(val intent: Intent?) : AuthScreenEvent()

}