package com.bangkit.lungcare.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    fun getUserProfile(token: String) = userUseCase.getUserProfile(token).asLiveData()

    fun getArticle(token: String, category: String) = articleUseCase.getAllArticle(token, category)
        .asLiveData()

    fun getToken() = userUseCase.getToken().asLiveData()

}