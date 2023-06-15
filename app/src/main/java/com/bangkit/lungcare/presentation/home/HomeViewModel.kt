package com.bangkit.lungcare.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.source.remote.response.ResponseArticleItem
import com.bangkit.lungcare.data.source.remote.response.UserProfileResponse
import com.bangkit.lungcare.domain.model.article.Article
import com.bangkit.lungcare.domain.model.user.Profile
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val articleUseCase: ArticleUseCase
) : ViewModel() {

    private val _userProfileResult = MutableLiveData<Result<Profile>>()
    val userProfileResult: LiveData<Result<Profile>> = _userProfileResult

    private val _articleResult = MutableLiveData<Result<List<Article>>>()
    val articleResult: LiveData<Result<List<Article>>> = _articleResult

    fun getAllArticle(token: String, category: String) {
        viewModelScope.launch {
            articleUseCase.getAllArticle(token, category).collect {
                _articleResult.value = it
            }
        }
    }

    fun getUserProfile(token: String) {
        viewModelScope.launch {
            userUseCase.getUserProfile(token).collect {
                _userProfileResult.value = it
            }
        }
    }

    fun getToken() = userUseCase.getToken().asLiveData()

}