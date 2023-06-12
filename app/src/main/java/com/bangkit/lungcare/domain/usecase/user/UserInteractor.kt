package com.bangkit.lungcare.domain.usecase.user

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository) : UserUseCase {
    override fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Register>> = userRepository.register(username, email, password)

    override fun login(email: String, password: String): Flow<Result<Login>> =
        userRepository.login(email, password)

    override fun getUserProfile(): Flow<Result<Profile>> = userRepository.getUserProfile()

    override suspend fun saveCredential(token: String) = userRepository.saveCredential(token)

    override suspend fun deleteCredential() = userRepository.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = userRepository.checkCredential()
}