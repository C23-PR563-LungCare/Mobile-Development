package com.bangkit.lungcare.presentation.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailXrayResultViewModel @Inject constructor(
    private val xrayUseCase: XrayUseCase,
    private val articleUseCase: ArticleUseCase
) :
    ViewModel() {

    private val resultId = MutableLiveData<String>()

    private val _articleResult = MutableLiveData<Result<List<Article>>>()
    val articleResult: LiveData<Result<List<Article>>> = _articleResult

    fun getArticle(category: String) = viewModelScope.launch {
        articleUseCase.getAllArticle(category).collect {
            _articleResult.value = it
        }
    }

    fun setResultId(id: String) {
        resultId.value = id
    }

    val detailResultXray by lazy {
        resultId.switchMap {
            xrayUseCase.getXrayById(it).asLiveData()
        }
    }
}