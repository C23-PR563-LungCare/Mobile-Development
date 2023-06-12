package com.bangkit.lungcare.presentation.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailXrayResultViewModel @Inject constructor(private val xrayUseCase: XrayUseCase) :
    ViewModel() {

    private val resultId = MutableLiveData<String>()

    fun setResultId(id: String) {
        resultId.value = id
    }

    val detailResultXray by lazy {
        resultId.switchMap {
            xrayUseCase.getXrayById(it).asLiveData()
        }
    }
}