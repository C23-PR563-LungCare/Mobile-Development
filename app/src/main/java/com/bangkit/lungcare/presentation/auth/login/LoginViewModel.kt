package com.bangkit.lungcare.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    private val _loginResult = MutableSharedFlow<Result<Login>>()
    val loginResult: SharedFlow<Result<Login>> get() = _loginResult

    fun login(email: String, password: String) = viewModelScope.launch {
        userUseCase.login(email, password).collect {
            _loginResult.emit(it)
        }
    }
}