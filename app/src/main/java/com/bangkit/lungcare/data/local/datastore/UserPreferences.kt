package com.bangkit.lungcare.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getToken(): Flow<String>

    suspend fun saveToken(token: String)

    suspend fun destroyToken()

    suspend fun setLogin(session: Boolean)

    fun getLogin(): Flow<Boolean>
}