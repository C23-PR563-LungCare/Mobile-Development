package com.bangkit.lungcare.domain.usecase

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.XrayItem
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayUpload
import kotlinx.coroutines.flow.Flow

interface XrayUseCase {

    fun register(username: String, email: String, password: String): Flow<Result<Register>>

    fun login(email: String, password: String): Flow<Result<Login>>

    fun uploadXray(xray: XrayUpload): Flow<Result<Xray>>

    fun getAllXray(): Flow<Result<List<XrayItem>>>

    suspend fun saveCredential(token: String)

    suspend fun deleteCredential()

    fun checkCredential(): Flow<Boolean>
}