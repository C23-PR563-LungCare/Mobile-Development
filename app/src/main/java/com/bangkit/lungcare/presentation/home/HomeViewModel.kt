package com.bangkit.lungcare.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: XrayUseCase) : ViewModel() {
    // fun getLogin() = userUseCase.getLogin().asLiveData()
}