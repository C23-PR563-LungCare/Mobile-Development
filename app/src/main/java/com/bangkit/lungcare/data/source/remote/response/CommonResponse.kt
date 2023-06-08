package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

// Response: uploadXray, register
data class CommonResponse(
    @field:SerializedName("message")
    val message: String? = null
)
