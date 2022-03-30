package com.example.libraryapp.ui.screens.apikeyManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.usecase.GetCurrentApiKeyUseCase
import com.example.libraryapp.domain.usecase.RefreshApiKeyUseCase
import kotlinx.coroutines.launch

class ApikeyManagementViewModel(
    private val getCurrentApiKeyUseCase: GetCurrentApiKeyUseCase,
    private val refreshApiKeyUseCase: RefreshApiKeyUseCase
) : ViewModel() {
    var currentApiKey = MutableLiveData<String?>()
    var error = MutableLiveData<String>()

    fun getCurrentApiKey() {
        viewModelScope.launch {
            when (val result = getCurrentApiKeyUseCase.invoke("")) {
                is Result.Error -> error.value = result.message!!
                is Result.Success -> currentApiKey.value = result.value
            }
        }
    }

    fun refreshOrganizationApiKey() {
        viewModelScope.launch {
            when (val result = refreshApiKeyUseCase.invoke("")) {
                is Result.Error -> error.value = result.message!!
                is Result.Success -> currentApiKey.value = result.value!!.apiKey
            }
        }
    }
}
