package com.bangkit.lungcare.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.xray.Xray
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val xrayUseCase: XrayUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _resultXray = MutableLiveData<Result<List<Xray>>>()
    val resultXray: LiveData<Result<List<Xray>>> = _resultXray

    fun getAllXray(token: String) = viewModelScope.launch {
        xrayUseCase.getAllXray(token).collect {
            _resultXray.value = it
        }
    }

    fun getToken() = userUseCase.getToken().asLiveData()

}