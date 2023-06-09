package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class XrayItemResponse(
    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("processResult")
    val processResult: String? = null,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null
)
