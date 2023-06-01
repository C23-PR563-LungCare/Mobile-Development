package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Signup
import com.bangkit.lungcare.domain.repository.UserRepository
import com.bangkit.lungcare.data.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    override fun signup(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Signup>> =
        userRepository.signup(username, email, password)

    override fun login(email: String, password: String): Flow<Result<Login>> =
        userRepository.login(email, password)

    override suspend fun destroyToken() = userRepository.destroyToken()

    override fun getLogin(): Flow<Boolean> = userRepository.getLogin()

}