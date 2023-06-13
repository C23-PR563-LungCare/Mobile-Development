package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import kotlinx.coroutines.flow.Flow
import java.io.File

interface XrayRepository {
    fun uploadXray(token: String, image: File): Flow<Result<XrayUpload>>
    fun getAllXray(): Flow<Result<List<Xray>>>
    fun getResultXrayPrediction(token: String, id: String): Flow<Result<Xray>>

}