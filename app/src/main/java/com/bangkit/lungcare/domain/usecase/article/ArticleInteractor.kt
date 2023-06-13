package com.bangkit.lungcare.domain.usecase.article

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleInteractor @Inject constructor(private val articleRepository: ArticleRepository) :
    ArticleUseCase {
    override fun getAllArticle(token: String, category: String): Flow<Result<List<Article>>> =
        articleRepository.getAllArticle(token, category)
}