package com.bangkit.lungcare.data.source.local.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getToken(): Flow<String>

    suspend fun saveCredential(token: String)

    suspend fun deleteCredential()
}