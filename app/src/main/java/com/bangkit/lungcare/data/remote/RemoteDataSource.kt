package com.bangkit.lungcare.data.remote

import com.bangkit.lungcare.data.remote.retrofit.XrayApiService

class RemoteDataSource private constructor(
    private val apiService: XrayApiService
) {
    suspend fun signup(username: String, email: String, password: String) =
        apiService.signup(username, email, password)

    suspend fun login(email: String, password: String) = apiService.login(email, password)

    // suspend fun getHistoryXrayImage(token: String) = apiService.getHistoryXrayImage()

    // suspend fun insertXrayImage()
}