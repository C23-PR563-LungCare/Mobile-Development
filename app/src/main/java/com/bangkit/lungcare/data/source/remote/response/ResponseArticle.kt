package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseArticle(

    @field:SerializedName("ResponseArticle")
    val responseArticle: List<ResponseArticleItem>
)

data class ResponseArticleItem(

    @field:SerializedName("newsID")
    val newsID: Int? = null,

    @field:SerializedName("newsURL")
    val newsURL: String? = null,

    @field:SerializedName("imageURL")
    val imageURL: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("newsCategory")
    val newsCategory: String? = null
)
