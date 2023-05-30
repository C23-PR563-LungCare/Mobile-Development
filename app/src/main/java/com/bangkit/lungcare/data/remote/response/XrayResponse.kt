package com.bangkit.lungcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class XrayResponse(

    @field:SerializedName("XrayResponse")
    val xrayResponse: List<XrayResponseItem>? = null
)

data class XrayResponseItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("processResult")
    val processResult: String? = null,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null
)
