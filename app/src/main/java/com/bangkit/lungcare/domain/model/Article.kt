package com.bangkit.lungcare.domain.model

data class Article(
    val newsId: Int,
    val newsUrl: String?,
    val imageUrl: String?,
    val title: String?,
    val newsCategory: String?
)