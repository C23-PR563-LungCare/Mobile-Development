package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XrayRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
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

    override fun getAllXray(token: String): Flow<Result<List<Xray>>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getAllXray(token)
            val result = DataMapper.mapXrayItemResponseToDomain(response.xrayResponse)

            emit(Result.Success(result))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    override fun getResultXrayPrediction(token: String, id: String): Flow<Result<Xray>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getResultXrayPrediction(token, id)
            val result = DataMapper.mapDetailXrayResponseToDomain(response)

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}