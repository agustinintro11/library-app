package com.example.libraryapp.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.User
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.usecase.DestroySessionUseCase
import com.example.libraryapp.domain.usecase.GetSessionOrgNameUseCase
import com.example.libraryapp.domain.usecase.GetSessionUserUseCase
import kotlinx.coroutines.launch

class SessionViewModel(
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val getSessionOrgNameUseCase: GetSessionOrgNameUseCase,
    private val destroySessionUseCase: DestroySessionUseCase
) : ViewModel() {
    var sessionUser = MutableLiveData<User?>()
    var sessionOrgName = MutableLiveData<String?>()
    var error = MutableLiveData<String>()
    val updatedSession = MutableLiveData<Boolean>()

    fun getUser() {
        viewModelScope.launch {
            when (val result = getSessionUserUseCase.invoke("")) {
                is Result.Error -> error.value = result.message!!
                is Result.Success -> sessionUser.value = result.value
            }
        }
    }

    private fun getOrgName() {
        viewModelScope.launch {
            when (val result = getSessionOrgNameUseCase.invoke("")) {
                is Result.Error -> error.value = result.message!!
                is Result.Success -> sessionOrgName.value = result.value
            }
        }
    }

    fun destroySession() {
        viewModelScope.launch {
            when (val response = destroySessionUseCase.invoke(Unit)) {
                is Result.Success -> updatedSession.value = true
                is Result.Error -> error.value = response.message ?: "An error occurred"
            }
        }
    }

    fun load() {
        getUser()
        getOrgName()
    }
}
