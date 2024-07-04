package com.example.emailvalidator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.emailvalidator.network.Response
import com.example.emailvalidator.network.RetrofitApi
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: RetrofitApi) : ViewModel() {
    private val _response = MutableLiveData(Response())
    val response: LiveData<Response> = _response
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String> = _hint
    private val _editText = MutableLiveData<String>()
    val editText: LiveData<String> = _editText

    fun onTextChanged(email: String) {
        _editText.value = email
        getRequest(email)
    }

    private fun getRequest(email: String) {
        viewModelScope.launch {
            _response.value = apiService.checkEmail(email)
            if (!_response.value?.did_you_mean.isNullOrBlank()) {
                _hint.value = _response.value?.did_you_mean!!
            }
        }
    }
}

class MainViewModelFactory(private val apiService: RetrofitApi) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(apiService) as T
    }
}