package com.bangkit.lungcare.data

import com.bangkit.lungcare.data.source.local.datastore.UserPreferences
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.Article
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.repository.XrayRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
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

    override fun uploadXray(image: File): Flow<Result<XrayUpload>> =
        flow {
            emit(Result.Loading)
            try {
                val token = userPreferences.getToken().first()
                val response = remoteDataSource.uploadXray("Bearer $token", image)
                val result = DataMapper.mapXrayResponseToDomain(response)

                emit(Result.Success(result))

            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override fun getAllXray(): Flow<Result<List<Xray>>> = flow {
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

    override fun getAllArticle(category: String): Flow<Result<List<Article>>> =
        flow {
            emit(Result.Loading)
            try {
                val token = userPreferences.getToken().first()
                val response = remoteDataSource.getAllArticle("Bearer $token", category)
                val result = DataMapper.mapArticleItemResponseToDomain(response.articleResponse)

                emit(Result.Success(result))

            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    //  override fun getToken(): Flow<String> = userPreferences.getToken()
    override suspend fun saveCredential(token: String) = userPreferences.saveCredential(token)

    override suspend fun deleteCredential() = userPreferences.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = userPreferences.checkCredential()
}