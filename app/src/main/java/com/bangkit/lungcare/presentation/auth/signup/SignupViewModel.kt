package com.bangkit.lungcare.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.Login
import com.bangkit.lungcare.domain.model.Signup
import com.bangkit.lungcare.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    private val _signupResult = MutableLiveData<Result<Signup>>()
    val signupResult: LiveData<Result<Signup>> get() = _signupResult

    fun signup(username: String, email: String, password: String) = viewModelScope.launch {
        userUseCase.signup(username, email, password).collect {
            _signupResult.value = it
        }
    }
}