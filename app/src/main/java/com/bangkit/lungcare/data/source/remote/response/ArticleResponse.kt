package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("ArticleResponse")
    val articleResponse: List<ArticleItemResponse>
)
