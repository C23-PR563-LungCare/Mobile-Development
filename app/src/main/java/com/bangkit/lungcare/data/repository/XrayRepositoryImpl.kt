package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.local.datastore.UserPreferences
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.domain.model.xray.Xray
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
    override fun uploadXray(token: String, image: File): Flow<Result<XrayUpload>> =
        flow {
            emit(Result.Loading)
            try {
                val response = remoteDataSource.uploadXray(token, image)
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
            val result = DataMapper.mapXrayItemResponseToDomain(response.xrayResponse)

            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override fun getXrayById(id: String): Flow<Result<Xray>> = flow {
        emit(Result.Loading)
        try {
            val token = userPreferences.getToken().first()
            val response = remoteDataSource.getXrayById("Bearer $token", id)
            val result = DataMapper.mapDetailXrayResponseToDomain(response)

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}