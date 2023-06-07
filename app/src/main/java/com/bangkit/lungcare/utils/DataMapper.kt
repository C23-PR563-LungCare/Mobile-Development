package com.bangkit.lungcare.utils

import com.bangkit.lungcare.data.remote.response.CommonResponse
import com.bangkit.lungcare.data.remote.response.LoginResponse
import com.bangkit.lungcare.data.remote.response.XrayItemResponse
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.Xray
import com.bangkit.lungcare.domain.model.XrayItem

object DataMapper {
    fun mapRegisterResponseToDomain(input: CommonResponse?) = Register(
        message = input?.message
    )

    fun mapLoginResponseToDomain(input: LoginResponse?) = Login(
        message = input?.message,
        token = input?.token
    )

    fun mapXrayResponseToDomain(input: CommonResponse) = Xray(
        message = input.message
    )

    fun mapXrayItemResponseToDomain(input: List<XrayItemResponse>): List<XrayItem> =
        input.map {
            XrayItem(
                date = it.date,
                gscLink = it.gcsLink,
                processResult = it.processResult
            )
        }
}