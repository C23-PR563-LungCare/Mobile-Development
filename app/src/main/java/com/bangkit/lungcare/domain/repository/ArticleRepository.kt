package com.bangkit.lungcare.domain.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAllArticle(token: String, category: String): Flow<Result<List<Article>>>
}