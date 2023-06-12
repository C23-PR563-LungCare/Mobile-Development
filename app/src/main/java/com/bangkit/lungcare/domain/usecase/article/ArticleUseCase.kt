package com.bangkit.lungcare.domain.usecase.article

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import kotlinx.coroutines.flow.Flow

interface ArticleUseCase {
    fun getAllArticle(category: String): Flow<Result<List<Article>>>
}