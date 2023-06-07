package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.local.datastore.UserPreferences
import com.bangkit.lungcare.data.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayItem
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XrayRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences
) : XrayRepository {
    override fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Register>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.signup(username, email, password)

            emit(Result.Success(DataMapper.mapRegisterResponseToDomain(response)))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override fun login(email: String, password: String): Flow<Result<Login>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.login(email, password)

            emit(Result.Success(DataMapper.mapLoginResponseToDomain(response)))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun uploadXray(xray: XrayUpload): Flow<Result<Xray>> = flow {
        emit(Result.Loading)
        try {
            val token = userPreferences.getToken().first()
            val response = remoteDataSource.uploadXray("Bearer $token", xray)

            emit(Result.Success(DataMapper.mapXrayResponseToDomain(response)))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllXray(): Flow<Result<List<XrayItem>>> = flow {
        emit(Result.Loading)
        try {
            val token = userPreferences.getToken().first()
            val response = remoteDataSource.getAllXray("Bearer $token")
            val result = DataMapper.mapXrayItemResponseToDomain(response.XrayResponse)

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveCredential(token: String) = userPreferences.saveCredential(token)

    override suspend fun deleteCredential() = userPreferences.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = userPreferences.checkCredential()
}