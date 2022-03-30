package com.example.libraryapp.ui.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.AdminAndOrgCreated
import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Organization
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.CreateAdminCaseIn
import com.example.libraryapp.domain.usecase.CreateAdminUseCase
import kotlinx.coroutines.launch

class SignupViewModel(
    private val createAdminUseCase: CreateAdminUseCase,
) : ViewModel() {
    var error = MutableLiveData<String>()
    var createdAdminAndOrg = MutableLiveData<AdminAndOrgCreated>()
    var orgApiKey = MutableLiveData<OrgApiKey>()

    var errorOrganization = MutableLiveData<String>()
    var errorEmail = MutableLiveData<String>()
    var errorName = MutableLiveData<String>()
    var errorPassword = MutableLiveData<String>()

    fun buttonClick(
        email: String,
        password: String,
        organizationName: String,
        name: String,
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
        if (name.isEmpty()) {
            errorName.value = "Ingrese un nombre"
            error = true
        }
        if (error)
            return

        viewModelScope.launch {
            createOrganization(organizationName, name, email, password)
        }
    }


    private suspend fun createOrganization(
        organizationName: String,
        name: String,
        email: String,
        password: String
    ) {
        val createOrganizationCaseIn =
            CreateAdminCaseIn(organizationName, name, email, password)
        when (val result = createAdminUseCase.invoke(createOrganizationCaseIn)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> createdAdminAndOrg.value = result.value!!
        }
    }

}
