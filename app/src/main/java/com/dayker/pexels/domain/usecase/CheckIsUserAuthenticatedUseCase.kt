package com.dayker.pexels.domain.usecase

import com.dayker.pexels.auth.domain.client.AuthClient
import javax.inject.Inject

class CheckIsUserAuthenticatedUseCase @Inject constructor(
    private val authClient: AuthClient
) {
    operator fun invoke(): Boolean {
        val user = authClient.getSignedInUser()
        return user != null
    }
}