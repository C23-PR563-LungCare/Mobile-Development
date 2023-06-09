package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class XrayItemResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null,

    @field:SerializedName("processResult")
    val processResult: String? = null,
)
