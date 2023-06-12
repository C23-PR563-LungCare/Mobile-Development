package com.bangkit.lungcare.utils

import com.bangkit.lungcare.data.source.remote.response.ArticleItemResponse
import com.bangkit.lungcare.data.source.remote.response.DetailXrayResponse
import com.bangkit.lungcare.data.source.remote.response.RegisterResponse
import com.bangkit.lungcare.data.source.remote.response.LoginResponse
import com.bangkit.lungcare.data.source.remote.response.UploadXrayResponse
import com.bangkit.lungcare.data.source.remote.response.UserProfileResponse
import com.bangkit.lungcare.data.source.remote.response.XrayItemResponse
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.domain.model.xray.Xray

object DataMapper {
    fun mapRegisterResponseToDomain(input: RegisterResponse?) = Register(
        message = input?.message
    )

    fun mapLoginResponseToDomain(input: LoginResponse?) = Login(
        email = input?.loginResult?.email,
        username = input?.loginResult?.username,
        token = input?.loginResult?.token
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

    fun mapArticleItemResponseToDomain(input: List<ArticleItemResponse>): List<Article> =
        input.map {
            Article(
                newsId = it.newsID,
                newsUrl = it.newsURL,
                imageUrl = it.imageURL,
                title = it.title,
                newsCategory = it.newsCategory
            )
        }

    fun mapUserProfileResponseToDomain(input: UserProfileResponse?) = Profile(
        username = input?.username
    )

    fun mapDetailXrayResponseToDomain(input: DetailXrayResponse) = Xray(
        date = input.date,
        processResult = input.processResult,
        id = input.id,
        gscLink = input.gcsLink
    )
}