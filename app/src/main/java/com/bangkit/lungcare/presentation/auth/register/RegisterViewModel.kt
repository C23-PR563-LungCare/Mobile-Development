package com.bangkit.lungcare.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.domain.model.Register
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import com.bangkit.lungcare.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {
    private val _registerResult = MutableLiveData<Result<Register>>()
    val registerResult: LiveData<Result<Register>> = _registerResult

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            xrayUseCase.register(username, email, password).collect {
                _registerResult.value = it
            }
        }
    }
}