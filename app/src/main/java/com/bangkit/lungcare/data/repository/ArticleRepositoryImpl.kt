package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.local.datastore.UserPreferences
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.repository.ArticleRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences
) : ArticleRepository {
    override fun getAllArticle(category: String): Flow<Result<List<Article>>> =
        flow {
            emit(Result.Loading)
            try {
                val token = userPreferences.getToken().first()
                val response = remoteDataSource.getAllArticle("Bearer $token", category)
                val result = DataMapper.mapArticleItemResponseToDomain(response.articleResponse)

                emit(Result.Success(result))

            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
}