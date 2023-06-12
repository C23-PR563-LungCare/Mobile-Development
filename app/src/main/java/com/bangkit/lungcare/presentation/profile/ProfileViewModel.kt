package com.bangkit.lungcare.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {
    fun logout() = viewModelScope.launch {
        xrayUseCase.deleteCredential()
    }
}