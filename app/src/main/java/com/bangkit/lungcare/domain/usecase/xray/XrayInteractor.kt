package com.bangkit.lungcare.domain.usecase.xray

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class XrayInteractor @Inject constructor(private val xrayRepository: XrayRepository) : XrayUseCase {
    override fun uploadXray(image: File): Flow<Result<XrayUpload>> =
        xrayRepository.uploadXray(image)

    override fun getAllXray(): Flow<Result<List<Xray>>> =
        xrayRepository.getAllXray()

    override fun getXrayById(id: String): Flow<Result<Xray>> = xrayRepository.getXrayById(id)
}