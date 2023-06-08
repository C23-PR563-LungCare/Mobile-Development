package com.bangkit.lungcare.data.source.remote.retrofit

import com.bangkit.lungcare.data.source.remote.response.CommonResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
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

interface XrayApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signup(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): CommonResponse

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
    ): CommonResponse

    @GET("history")
    suspend fun getAll(
        @Header("Authorization") token: String,
    ): XrayResponse
}