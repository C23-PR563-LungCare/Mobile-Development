package com.bangkit.lungcare.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {

    private val _xrayResult = MutableLiveData<Result<List<Xray>>>()
    val xrayResult: LiveData<Result<List<Xray>>> = _xrayResult

    fun getAllXray() = viewModelScope.launch {
        xrayUseCase.getAllXray().collect {
            _xrayResult.value = it
        }
    }

}