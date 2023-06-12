package com.bangkit.lungcare.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _articleResult = MutableLiveData<Result<List<Article>>>()
    val articleResult: LiveData<Result<List<Article>>> = _articleResult

    fun getUserProfile() = userUseCase.getUserProfile().asLiveData()

    fun getArticle(category: String) = viewModelScope.launch {
        articleUseCase.getAllArticle(category).collect {
            _articleResult.value = it
        }
    }
}