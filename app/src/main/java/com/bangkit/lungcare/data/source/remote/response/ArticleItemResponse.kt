package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleItemResponse(
    @field:SerializedName("newsID")
    val newsID: Int,

    @field:SerializedName("newsCategory")
    val newsCategory: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("newsURL")
    val newsURL: String? = null,

    @field:SerializedName("imageURL")
    val imageURL: String? = null,
)
