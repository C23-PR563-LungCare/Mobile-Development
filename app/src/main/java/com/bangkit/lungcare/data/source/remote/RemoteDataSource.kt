package com.bangkit.lungcare.data.source.remote

import com.bangkit.lungcare.data.source.remote.response.CommonResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
import com.bangkit.lungcare.data.source.remote.response.XrayResponse
import com.bangkit.lungcare.data.source.remote.retrofit.XrayApiService
import com.bangkit.lungcare.domain.model.XrayUploadRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: XrayApiService
) {
    suspend fun signup(username: String, email: String, password: String): CommonResponse =
        apiService.signup(username, email, password)

    suspend fun login(email: String, password: String): LoginResponse =
        apiService.login(email, password)

    suspend fun uploadXray(token: String, xray: XrayUploadRequest): CommonResponse {
        val imageMediaType = "image/jpeg".toMediaType()
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            xray.image.name,
            xray.image.asRequestBody(imageMediaType)
        )

        return apiService.uploadXray(token, imageMultipart)
    }

    suspend fun getAllXray(token: String): XrayResponse = apiService.getAll(token)
}