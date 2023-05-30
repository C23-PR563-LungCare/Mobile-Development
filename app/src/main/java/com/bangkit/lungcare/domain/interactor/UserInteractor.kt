package com.bangkit.lungcare.domain.interactor

import com.bangkit.lungcare.domain.entity.LoginEntity
import com.bangkit.lungcare.domain.entity.SignupEntity
import com.bangkit.lungcare.domain.repository.IUserRepository
import com.bangkit.lungcare.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun signup(
        username: String,
        email: String,
        password: String
    ): Flow<Result<SignupEntity>> = userRepository.signup(username, email, password)

    override fun login(email: String, password: String): Flow<Result<LoginEntity>> =
        userRepository.login(email, password)

    override suspend fun destroyToken() = userRepository.destroyToken()

    override fun getLogin(): Flow<Boolean> = userRepository.getLogin()

}