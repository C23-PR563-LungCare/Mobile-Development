package com.bangkit.lungcare.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {
    fun getAllXray() = xrayUseCase.getAllXray().asLiveData()
}