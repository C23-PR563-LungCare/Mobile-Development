package com.bangkit.lungcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class XrayResponse(

    @field:SerializedName("ListXrayResponse")
    val XrayResponse: List<XrayItemResponse>
)
