package com.bangkit.lungcare.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {
    override fun getToken(): Flow<String> = dataStore.data.map { it[TOKEN_KEY] ?: "" }

    override suspend fun saveCredential(token: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
            it[SESSION_KEY] = true
        }
    }

    override suspend fun deleteCredential() {
        dataStore.edit {
            it.remove(TOKEN_KEY)
            it[SESSION_KEY] = false
        }
    }

    override fun checkCredential(): Flow<Boolean> = dataStore.data.map { it[SESSION_KEY] ?: false }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val SESSION_KEY = booleanPreferencesKey("session")
    }
}