package com.bangkit.lungcare.data.network.retrofit

import com.bangkit.lungcare.data.network.response.HistoryXrayResponse
import com.bangkit.lungcare.data.network.response.XrayItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface XrayApiService {

    @Multipart
    @POST("insertImage")
    suspend fun postXray(
        @Part file: MultipartBody.Part,
        @Part("username") username: RequestBody
    )

    @GET("history/{username}")
    suspend fun getHistoryXray(
        @Path("username") username: String
    ): List<XrayItem>
}