package com.bangkit.lungcare.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val _result = MutableLiveData<Result<List<XrayItem>>>()
    val result: LiveData<Result<List<XrayItem>>> = _result

    fun getHistoryXray() {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getHistoryXray("hellow")
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d("HistoryViewModel", "getHistoryXray: ${e.message.toString()}")
            }
        }
    }
}