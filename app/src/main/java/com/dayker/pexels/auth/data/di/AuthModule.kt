package com.dayker.pexels.auth.data.di

import android.content.Context
import com.dayker.pexels.auth.data.client.FirebaseAuthClient
import com.dayker.pexels.auth.domain.client.AuthClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    companion object {
        private const val SERVER_CLIENT_ID =
            "207733028885-9dcenieo75rjqmnto73afnpfhuovrv11.apps.googleusercontent.com"

        @Singleton
        @Provides
        fun provideSignInClient(@ApplicationContext context: Context): SignInClient {
            return Identity.getSignInClient(context)
        }

        @Singleton
        @Provides
        fun provideBeginSignInRequest(): BeginSignInRequest {
            return BeginSignInRequest.Builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(SERVER_CLIENT_ID)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build()
        }
    }

    @Binds
    abstract fun bindAuthClient(
        client: FirebaseAuthClient
    ): AuthClient
}