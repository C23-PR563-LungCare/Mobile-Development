package com.bangkit.lungcare.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) :
    ViewModel() {

    fun getToken() = userUseCase.getToken().asLiveData()
    fun logout() = viewModelScope.launch {
        userUseCase.deleteCredential()
    }
}