package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.local.datastore.UserPreferences
import com.bangkit.lungcare.data.remote.RemoteDataSource
import com.bangkit.lungcare.domain.repository.UserRepository
import com.bangkit.lungcare.data.Result
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

    override fun signup(
        username: String,
        email: String,
        password: String
    ) = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.signup(username, email, password)

            emit(Result.Success(DataMapper.mapSignupResponseToDomain(response)))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override fun login(email: String, password: String) = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.login(email, password)
            userPreferences.run {
                saveToken(response.token.toString())
                setLogin(true)
            }

            emit(Result.Success(DataMapper.mapLoginResponseToDomain(response)))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun destroyToken() {
        userPreferences.run {
            destroyToken()
            setLogin(false)
        }
    }
    override fun getLogin(): Flow<Boolean> = userPreferences.getLogin()
}