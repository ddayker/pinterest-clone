package com.dayker.pexels.auth.presentation

import android.content.IntentSender

sealed class AuthScreenAction {

    data class ShowSignInRequest(val intentSender: IntentSender?) : AuthScreenAction()

    data class ShowErrorMessage(val message: String) : AuthScreenAction()

    object OpenHomeScreen : AuthScreenAction()

}