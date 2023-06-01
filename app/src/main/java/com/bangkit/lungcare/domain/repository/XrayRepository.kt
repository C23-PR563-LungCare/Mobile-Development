package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayUpload
import kotlinx.coroutines.flow.Flow
import java.io.File

interface XrayRepository {

    fun uploadXray(photo: File): Flow<Result<XrayUpload>>

    fun getAllXray(): Flow<Result<List<Xray>>>
}