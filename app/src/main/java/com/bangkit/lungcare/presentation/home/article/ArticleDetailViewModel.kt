package com.bangkit.lungcare.presentation.home.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.lungcare.domain.model.article.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor() : ViewModel() {

    private val articleData = MutableLiveData<Article>()

    fun setArticleData(article: Article) {
        articleData.value = article
    }
}