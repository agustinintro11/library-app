package com.example.libraryapp.ui.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.Credentials
import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Organization
import com.example.libraryapp.data.model.Session
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.CreateSessionCaseIn
import com.example.libraryapp.domain.usecase.CreateSessionUseCase
import com.example.libraryapp.domain.usecase.GetOrganizationApiKeyUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val createSessionUseCase: CreateSessionUseCase,
    private val getOrganizationApiKeyUseCase: GetOrganizationApiKeyUseCase,
) : ViewModel() {
    var error = MutableLiveData<String>()
    var createdSession = MutableLiveData<Session>()
    var orgApiKey = MutableLiveData<OrgApiKey>()

    var errorOrganization = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()
    var errorPassword = MutableLiveData<String>()

    fun buttonClick(
        email: String,
        password: String,
        organizationName: String

    ) {
        var error = false

        if (organizationName.isEmpty()) {
            errorOrganization.value = "Ingrese una organización"
            error = true
        }
        if (email.isEmpty()) {
            errorEmail.value = "Ingrese un email"
            error = true
        }
        if (password.isEmpty()) {
            errorPassword.value = "Ingrese una contraseña"
            error = true
        }
        if (error)
            return

        viewModelScope.launch {
            getOrganizationApiKey(organizationName, email, password)
        }
    }

    private suspend fun getOrganizationApiKey(
        organizationName: String,
        email: String,
        password: String
    ) {
        when (val result = getOrganizationApiKeyUseCase.invoke(organizationName)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> {
                orgApiKey.value = result.value!!
                createSession(orgApiKey.value!!, organizationName, email, password)
            }
            is Result.NotFoundError -> errorOrganization.value = "No existe esta organización"
        }
    }

    private suspend fun createSession(
        orgApiKey: OrgApiKey,
        organizationName: String,
        email: String,
        password: String
    ) {
        val createSessionCaseIn =
            CreateSessionCaseIn(
                Organization(organizationName, orgApiKey.apiKey),
                Credentials(email, password)
            )
        when (val result = createSessionUseCase.invoke(createSessionCaseIn)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            //TODO: not successfully answer is reaching this point, an other implementation should be done
            is Result.Success -> {
                if (result.value != null) {
                    createdSession.value = result.value!!
                } else {
                    errorPassword.value = "El email o la contraseña no son correctos"
                    errorEmail.value = " "
                }
            }
        }

    }
}
