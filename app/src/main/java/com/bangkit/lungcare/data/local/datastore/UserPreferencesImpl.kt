package com.bangkit.lungcare.data.local.datastore

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
class UserPreferencesImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserPreferences {

    override fun getToken(): Flow<String> {
        return dataStore.data.map { it[TOKEN_KEY] ?: "" }
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    override suspend fun destroyToken() {
        dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }

    override suspend fun setLogin(session: Boolean) {
        dataStore.edit {
            it[SESSION_KEY] = session
        }
    }

    override fun getLogin(): Flow<Boolean> {
        return dataStore.data.map {
            it[SESSION_KEY] ?: false
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val SESSION_KEY = booleanPreferencesKey("session")
    }
}