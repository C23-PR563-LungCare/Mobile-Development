package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.entity.LoginEntity
import com.bangkit.lungcare.domain.entity.SignupEntity
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun signup(
        username: String,
        email: String,
        password: String
    ): Flow<Result<SignupEntity>>

    fun login(email: String, password: String): Flow<Result<LoginEntity>>

    suspend fun destroyToken()

    fun getLogin(): Flow<Boolean>
}