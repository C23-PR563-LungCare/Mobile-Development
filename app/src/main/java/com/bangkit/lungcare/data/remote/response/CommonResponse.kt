package com.bangkit.lungcare.data.remote.response

import com.google.gson.annotations.SerializedName

// Response: uploadXray, register
data class CommonResponse(
    @field:SerializedName("message")
    val message: String? = null
)
