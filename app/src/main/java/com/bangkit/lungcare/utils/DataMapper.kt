package com.bangkit.lungcare.utils

import com.bangkit.lungcare.data.source.remote.response.RegisterResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
import com.bangkit.lungcare.data.source.remote.response.UploadXrayResponse
import com.bangkit.lungcare.data.source.remote.response.XrayItemResponse
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.model.Xray

object DataMapper {
    fun mapRegisterResponseToDomain(input: RegisterResponse?) = Register(
        message = input?.message
    )

    fun mapLoginResponseToDomain(input: LoginResponse?) = Login(
        message = input?.message,
        token = input?.token
    )

    fun mapXrayResponseToDomain(input: UploadXrayResponse) = XrayUpload(
        result = input.result,
        id = input.id,
        gcsLink = input.gcsLink,
        message = input.message,
        username = input.username
    )

    fun mapXrayItemResponseToDomain(input: List<XrayItemResponse>): List<Xray> =
        input.map {
            Xray(
                id = it.id,
                date = it.date,
                gscLink = it.gcsLink,
                processResult = it.processResult
            )
        }
}