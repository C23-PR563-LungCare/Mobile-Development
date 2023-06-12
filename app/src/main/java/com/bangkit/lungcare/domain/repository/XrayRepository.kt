package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Article
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayUpload
import kotlinx.coroutines.flow.Flow
import java.io.File

interface XrayRepository {
    fun register(username: String, email: String, password: String): Flow<Result<Register>>
    fun login(email: String, password: String): Flow<Result<Login>>
    fun uploadXray(image: File): Flow<Result<XrayUpload>>
    fun getAllXray(): Flow<Result<List<Xray>>>
    fun getAllArticle(category: String): Flow<Result<List<Article>>>

    // fun getToken(): Flow<String>
    suspend fun saveCredential(token: String)
    suspend fun deleteCredential()
    fun checkCredential(): Flow<Boolean>
}