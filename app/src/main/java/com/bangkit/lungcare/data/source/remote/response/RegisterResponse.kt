package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("message")
    val message: String? = null
)
