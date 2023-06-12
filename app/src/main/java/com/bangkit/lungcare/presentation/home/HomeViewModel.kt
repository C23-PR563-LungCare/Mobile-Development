package com.bangkit.lungcare.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Article
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {

    private val _articleResult = MutableLiveData<Result<List<Article>>>()
    val articleResult: LiveData<Result<List<Article>>> = _articleResult

    // private val _tokenResult = MutableLiveData<String>()
    // val tokenResult: LiveData<String> = _tokenResult
    fun getArticle(category: String) = viewModelScope.launch {
        xrayUseCase.getAllArticle(category).collect {
            _articleResult.value = it
        }
    }
}