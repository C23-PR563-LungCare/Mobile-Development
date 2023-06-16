package com.bangkit.lungcare.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.user.Login
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    private var _loginResult = MutableLiveData<Result<Login>>()
    val loginResult: LiveData<Result<Login>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userUseCase.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun saveCredential(token: String) {
        viewModelScope.launch {
            userUseCase.saveCredential(token)
        }
    }

    fun getToken() = userUseCase.getToken().asLiveData()
}