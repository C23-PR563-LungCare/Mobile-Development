package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.domain.entity.LoginEntity
import com.bangkit.lungcare.domain.entity.SignupEntity
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun signup(username: String, email: String, password: String): Flow<Result<SignupEntity>>

    fun login(email: String, password: String): Flow<Result<LoginEntity>>

    suspend fun destroyToken()

    fun getLogin(): Flow<Boolean>
}