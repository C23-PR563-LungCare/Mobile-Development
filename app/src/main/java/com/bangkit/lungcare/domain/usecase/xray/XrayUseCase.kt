package com.bangkit.lungcare.domain.usecase.xray

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import kotlinx.coroutines.flow.Flow
import java.io.File

interface XrayUseCase {
    fun uploadXray(image: File): Flow<Result<XrayUpload>>
    fun getAllXray(): Flow<Result<List<Xray>>>
    fun getXrayById(id: String): Flow<Result<Xray>>
}