package com.bangkit.lungcare.domain.usecase.xray

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import kotlinx.coroutines.flow.Flow
import java.io.File

interface XrayUseCase {
    fun uploadXray(token: String, image: File): Flow<Result<XrayUpload>>
    fun getAllXray(): Flow<Result<List<Xray>>>
    fun getResultXrayPrediction(token: String, id: String): Flow<Result<Xray>>
}