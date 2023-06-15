package com.bangkit.lungcare.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.user.Register
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) :
    ViewModel() {
    private val _registerResult = MutableLiveData<Result<Register>>()
    val registerResult: LiveData<Result<Register>> = _registerResult

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            userUseCase.register(username, email, password).collect {
                _registerResult.value = it
            }
        }
    }
}