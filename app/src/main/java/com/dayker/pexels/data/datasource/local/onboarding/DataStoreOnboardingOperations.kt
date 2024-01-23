package com.dayker.pexels.data.datasource.local.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreOnboardingOperations @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OnboardingOperations {

    companion object {
        const val DATASTORE_NAME = "preferences"
        val ON_BOARDING_KEY = booleanPreferencesKey(name = "onboarding_key")
    }

    override suspend fun saveOnboardingState(isCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[ON_BOARDING_KEY] = isCompleted
        }
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emptyPreferences()
                else throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[ON_BOARDING_KEY] ?: false
                onBoardingState
            }.first()
    }
}