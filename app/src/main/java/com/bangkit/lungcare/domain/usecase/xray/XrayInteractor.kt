package com.bangkit.lungcare.domain.usecase.xray

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class XrayInteractor @Inject constructor(private val xrayRepository: XrayRepository) : XrayUseCase {
    override fun uploadXray(token: String, image: File): Flow<Result<XrayUpload>> =
        xrayRepository.uploadXray(token, image)

    override fun getAllXray(): Flow<Result<List<Xray>>> =
        xrayRepository.getAllXray()

    override fun getResultXrayPrediction(token: String, id: String): Flow<Result<Xray>> =
        xrayRepository.getResultXrayPrediction(token, id)
}