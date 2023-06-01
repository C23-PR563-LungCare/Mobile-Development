package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Signup
import com.bangkit.lungcare.data.Result
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun signup(username: String, email: String, password: String): Flow<Result<Signup>>

    fun login(email: String, password: String): Flow<Result<Login>>

    suspend fun destroyToken()

    fun getLogin(): Flow<Boolean>
}