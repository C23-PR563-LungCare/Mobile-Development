package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Signup
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signup(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Signup>>

    fun login(email: String, password: String): Flow<Result<Login>>

    suspend fun destroyToken()

    fun getLogin(): Flow<Boolean>
}