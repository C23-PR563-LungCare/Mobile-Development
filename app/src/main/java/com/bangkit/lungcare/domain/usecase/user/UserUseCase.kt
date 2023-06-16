package com.bangkit.lungcare.domain.usecase.user

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.model.user.Register
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun register(username: String, email: String, password: String): Flow<Result<Register>>

    fun login(email: String, password: String): Flow<Result<Login>>

    fun getUserProfile(token: String): Flow<Result<Profile>>

    fun getToken(): Flow<String>

    suspend fun saveCredential(token: String)

    suspend fun deleteCredential()

}