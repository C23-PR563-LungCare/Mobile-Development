package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailXrayResponse(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("processResult")
    val processResult: String? = null,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null
)
