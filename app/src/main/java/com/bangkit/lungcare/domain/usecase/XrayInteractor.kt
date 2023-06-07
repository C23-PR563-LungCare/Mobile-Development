package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.XrayItem
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class XrayInteractor @Inject constructor(private val xrayRepository: XrayRepository) : XrayUseCase {

    override fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Register>> = xrayRepository.register(username, email, password)

    override fun login(email: String, password: String): Flow<Result<Login>> =
        xrayRepository.login(email, password)

    override fun uploadXray(xray: XrayUpload): Flow<Result<Xray>> =
        xrayRepository.uploadXray(xray)

    override fun getAllXray(): Flow<Result<List<XrayItem>>> =
        xrayRepository.getAllXray()

    override suspend fun saveCredential(token: String) = xrayRepository.saveCredential(token)

    override suspend fun deleteCredential() = xrayRepository.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = xrayRepository.checkCredential()
}