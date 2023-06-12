package com.bangkit.lungcare.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("username")
	val username: String? = null
)
