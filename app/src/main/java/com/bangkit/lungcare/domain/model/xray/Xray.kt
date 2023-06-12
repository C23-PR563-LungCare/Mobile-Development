package com.bangkit.lungcare.domain.model.xray

data class Xray(
    val id: String,
    val date: String?,
    val processResult: String?,
    val gscLink: String?
)