package com.bangkit.lungcare.presentation.home.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.domain.model.XrayUploadRequest
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostXrayViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) : ViewModel() {

    private val _xrayResult = MutableLiveData<Result<XrayUpload>>()
    val xrayResult: LiveData<Result<XrayUpload>> = _xrayResult

    fun uploadXrayToPredict(image: File) {
        viewModelScope.launch {
            val xrayRequest = XrayUploadRequest(image)
            xrayUseCase.uploadXray(xrayRequest).collect {
                _xrayResult.value = it
            }
        }
    }
}