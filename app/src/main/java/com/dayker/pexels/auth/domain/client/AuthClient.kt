package com.dayker.pexels.auth.domain.client

import android.content.Intent
import android.content.IntentSender
import com.dayker.pexels.auth.domain.model.SignInResult
import com.dayker.pexels.auth.domain.model.User


interface AuthClient {
    suspend fun initiateSignIn(): IntentSender?
    suspend fun completeSignIn(intent: Intent): SignInResult
    suspend fun signOut()
    suspend fun getSignedInUser(): User?
}