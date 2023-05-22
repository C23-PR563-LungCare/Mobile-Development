package com.bangkit.lungcare.data.network.response

import com.google.gson.annotations.SerializedName

data class XrayItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("processResult")
    val processResult: String? = null,

    @field:SerializedName("gcsLink")
    val gcsLink: String? = null
)
