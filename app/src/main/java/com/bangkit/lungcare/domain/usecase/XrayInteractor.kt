package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Article
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.repository.XrayRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class XrayInteractor @Inject constructor(private val xrayRepository: XrayRepository) : XrayUseCase {

    override fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Result<Register>> = xrayRepository.register(username, email, password)

    override fun login(email: String, password: String): Flow<Result<Login>> =
        xrayRepository.login(email, password)

    override fun uploadXray(image: File): Flow<Result<XrayUpload>> =
        xrayRepository.uploadXray(image)

    override fun getAllXray(): Flow<Result<List<Xray>>> =
        xrayRepository.getAllXray()

    override fun getAllArticle(category: String): Flow<Result<List<Article>>> =
        xrayRepository.getAllArticle(category)

    // override fun getToken(): Flow<String> = xrayRepository.getToken()

    override suspend fun saveCredential(token: String) = xrayRepository.saveCredential(token)

    override suspend fun deleteCredential() = xrayRepository.deleteCredential()

    override fun checkCredential(): Flow<Boolean> = xrayRepository.checkCredential()
}