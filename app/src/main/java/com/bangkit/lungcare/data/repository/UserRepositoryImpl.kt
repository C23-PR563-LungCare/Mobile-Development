package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.local.datastore.UserPreferences
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.repository.UserRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences
) : UserRepository {

    override fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Register>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.signup(username, email, password)
            val result = DataMapper.mapRegisterResponseToDomain(response)


            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override fun login(email: String, password: String): Flow<Result<Login>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.login(email, password)
            val result = DataMapper.mapLoginResponseToDomain(response)

            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserProfile(token: String): Flow<Result<Profile>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getUserProfile(token)
            val result = DataMapper.mapUserProfileResponseToDomain(response)

            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getToken(): Flow<String> = userPreferences.getToken()

    override suspend fun saveCredential(token: String) = userPreferences.saveCredential(token)

    override suspend fun deleteCredential() = userPreferences.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = userPreferences.checkCredential()
}