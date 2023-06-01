package com.bangkit.lungcare.data.remote

import com.bangkit.lungcare.data.remote.response.CommonResponse
import com.bangkit.lungcare.data.remote.retrofit.XrayApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: XrayApiService
) {
    suspend fun signup(username: String, email: String, password: String) =
        apiService.signup(username, email, password)

    suspend fun login(email: String, password: String) = apiService.login(email, password)
    suspend fun uploadXray(token: String, photo: File): CommonResponse {
        val imageMediaType = "image/jpeg".toMediaType()
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            photo.name,
            photo.asRequestBody(imageMediaType)
        )
        return apiService.uploadXray(token, imageMultipart)
    }
    suspend fun getAllXray(token: String) = apiService.getAll(token)
}