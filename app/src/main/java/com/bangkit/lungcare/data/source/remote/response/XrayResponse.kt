package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class XrayResponse(

    @field:SerializedName("XrayResponse")
    val xrayResponse: List<XrayItemResponse>
)
