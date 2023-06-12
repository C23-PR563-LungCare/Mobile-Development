package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UploadXrayResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null,

    @field:SerializedName("result")
    val result: String? = null,
)
