package com.bangkit.lungcare.data.network.response

import com.google.gson.annotations.SerializedName

data class HistoryXrayResponse(

    @field:SerializedName("HistoryXrayResponse")
    val historyXrayResponse: List<XrayItem>
)

