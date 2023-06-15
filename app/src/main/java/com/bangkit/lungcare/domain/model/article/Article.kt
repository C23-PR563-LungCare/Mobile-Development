package com.bangkit.lungcare.domain.model.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val newsId: Int,
    val newsUrl: String?,
    val imageUrl: String?,
    val title: String?,
    val newsCategory: String?
) : Parcelable