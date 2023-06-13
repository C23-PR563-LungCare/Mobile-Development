package com.bangkit.lungcare.data.source.remote

import com.bangkit.lungcare.data.source.remote.response.ArticleResponse
import com.bangkit.lungcare.data.source.remote.response.RegisterResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
import com.bangkit.lungcare.data.source.remote.response.UploadXrayResponse
import com.bangkit.lungcare.data.source.remote.response.UserProfileResponse
import com.bangkit.lungcare.data.source.remote.response.XrayResponse
import com.bangkit.lungcare.data.source.remote.retrofit.XrayApiService
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
    suspend fun signup(username: String, email: String, password: String): RegisterResponse =
        apiService.signup(username, email, password)

    suspend fun login(email: String, password: String): LoginResponse =
        apiService.login(email, password)

    suspend fun uploadXray(token: String, image: File): UploadXrayResponse {
        val imageMediaType = "image/jpeg".toMediaType()
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            image.name,
            image.asRequestBody(imageMediaType)
        )

        return apiService.uploadXray(token, imageMultipart)
    }

    suspend fun getAllXray(token: String): XrayResponse = apiService.getAll(token)
    suspend fun getResultXrayPrediction(token: String, id: String) =
        apiService.getResultXrayPrediction(token, id)

    suspend fun getAllArticle(token: String, category: String): ArticleResponse =
        apiService.getAllArticle(token, category)

    suspend fun getUserProfile(token: String): UserProfileResponse =
        apiService.getUserProfile(token)
}