package com.bangkit.lungcare.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) :
    IUserPreferences {

    override suspend fun getToken(): Flow<String> {
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

    override suspend fun getLogin(): Flow<Boolean> {
        return dataStore.data.map { it[SESSION_KEY] ?: false }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val SESSION_KEY = booleanPreferencesKey("session")

        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences =
            instance ?: synchronized(this) {
                instance ?: UserPreferences(dataStore)
            }.also { instance = it }
    }
}