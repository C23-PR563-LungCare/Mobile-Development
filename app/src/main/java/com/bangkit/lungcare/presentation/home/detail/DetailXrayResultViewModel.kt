package com.bangkit.lungcare.presentation.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailXrayResultViewModel @Inject constructor(
    private val xrayUseCase: XrayUseCase,
    private val articleUseCase: ArticleUseCase,
    private val userUseCase: UserUseCase
) :
    ViewModel() {

    private val _resultXray = MutableLiveData<Result<Xray>>()
    val resultXray: LiveData<Result<Xray>> = _resultXray

    private val _resultArticle = MutableLiveData<Result<List<Article>>>()
    val resultArticle: LiveData<Result<List<Article>>> = _resultArticle

    fun getResultXrayPrediction(token: String, id: String) = viewModelScope.launch {
        xrayUseCase.getResultXrayPrediction(token, id).collect {
            _resultXray.value = it
        }
    }

    fun getRelateArticle(token: String, category: String) = viewModelScope.launch {
        articleUseCase.getAllArticle(token, category).collect {
            _resultArticle.value = it
        }
    }

    fun getToken() = userUseCase.getToken().asLiveData()
}