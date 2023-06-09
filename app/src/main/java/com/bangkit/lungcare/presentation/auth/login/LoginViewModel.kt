package com.bangkit.lungcare.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import com.bangkit.lungcare.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {

    private var _loginResult = MutableLiveData<Result<Login>>()
    val loginResult: LiveData<Result<Login>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            xrayUseCase.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun saveCredential(token: String) {
        viewModelScope.launch {
            xrayUseCase.saveCredential(token)
        }
    }

    fun checkCredential() = xrayUseCase.checkCredential().asLiveData()
}