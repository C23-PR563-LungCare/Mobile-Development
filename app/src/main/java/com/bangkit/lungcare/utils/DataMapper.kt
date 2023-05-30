package com.bangkit.lungcare.utils

import com.bangkit.lungcare.data.remote.response.CommonResponse
import com.bangkit.lungcare.data.remote.response.LoginResponse
import com.bangkit.lungcare.domain.entity.LoginEntity
import com.bangkit.lungcare.domain.entity.SignupEntity

fun CommonResponse?.toSignupDomainEntity() = SignupEntity(this?.message)

fun LoginResponse?.toLoginDomainEntity() = LoginEntity(this?.message, this?.token)