package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TesResponse(

	@field:SerializedName("TesResponse")
	val tesResponse: List<TesResponseItem?>? = null
)

data class TesResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("processResult")
	val processResult: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("gcsLink")
	val gcsLink: String? = null
)
