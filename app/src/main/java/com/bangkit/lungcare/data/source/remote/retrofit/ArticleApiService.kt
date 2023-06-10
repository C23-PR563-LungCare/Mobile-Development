package com.bangkit.lungcare.data.source.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("top-headlines?country=id&category=health")
    suspend fun getArticles(@Query("apiKey") apiKey: String)
}