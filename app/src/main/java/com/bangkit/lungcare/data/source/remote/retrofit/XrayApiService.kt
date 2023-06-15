package com.bangkit.lungcare.data.source.remote.retrofit

import com.bangkit.lungcare.data.source.remote.response.ArticleItemResponse
import com.bangkit.lungcare.data.source.remote.response.ArticleResponse
import com.bangkit.lungcare.data.source.remote.response.DetailXrayResponse
import com.bangkit.lungcare.data.source.remote.response.RegisterResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
import com.bangkit.lungcare.data.source.remote.response.ResponseArticle
import com.bangkit.lungcare.data.source.remote.response.ResponseArticleItem
import com.bangkit.lungcare.data.source.remote.response.UploadXrayResponse
import com.bangkit.lungcare.data.source.remote.response.UserProfileResponse
import com.bangkit.lungcare.data.source.remote.response.XrayItemResponse
import com.bangkit.lungcare.data.source.remote.response.XrayResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface XrayApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signup(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("insertImage")
    suspend fun uploadXray(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): UploadXrayResponse

    @GET("history")
    suspend fun getAll(
        @Header("Authorization") token: String,
    ): List<XrayItemResponse>

    @GET("news/{category}")
    suspend fun getAllArticle(
        @Header("Authorization") token: String,
        @Path("category") category: String
    ): List<ArticleItemResponse>

    @GET("detailHistory/{id}")
    suspend fun getResultXrayPrediction(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailXrayResponse

    @GET("getUser")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): UserProfileResponse
}