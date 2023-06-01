package com.bangkit.lungcare.utils

import android.util.Log
import com.bangkit.lungcare.data.remote.response.CommonResponse
import com.bangkit.lungcare.data.remote.response.LoginResponse
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Signup

object DataMapper {
    fun mapSignupResponseToDomain(input: CommonResponse?) = Signup(
        message = input?.message
    )

    fun mapLoginResponseToDomain(input: LoginResponse?) = Login(
        message = input?.message,
        token = input?.token
    )
}