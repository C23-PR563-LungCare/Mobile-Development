package com.bangkit.lungcare.data.repository

import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.repository.ArticleRepository
import com.bangkit.lungcare.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {
    override fun getAllArticle(token: String, category: String): Flow<Result<List<Article>>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = remoteDataSource.getAllArticle(token, category)
                val result = DataMapper.mapArticleItemResponseToDomain(response)

                emit(Result.Success(result))

            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}