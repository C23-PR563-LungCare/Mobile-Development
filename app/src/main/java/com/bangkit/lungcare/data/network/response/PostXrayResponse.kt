package com.bangkit.lungcare.data.network.response

import com.google.gson.annotations.SerializedName

data class PostXrayResponse(

    @field:SerializedName("message")
    val message: String? = null
)