package com.example.libraryapp.ui.screens.inviteUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.SendUserInvitationCaseIn
import com.example.libraryapp.domain.usecase.SendUserInvitationUseCase
import kotlinx.coroutines.launch

class InviteUserViewModel(
    private val sendInvitationUseCase: SendUserInvitationUseCase
) : ViewModel() {

    var errorEmail = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    var invitationSent = MutableLiveData<Boolean>()

    fun buttonClick(email: String, isAdmin: Boolean) {
        var error = false

        if (email.isEmpty()) {
            errorEmail.value = "Ingrese un email"
            error = true
        }

        if (error)
            return

        viewModelScope.launch {
            sendInvitation(email, isAdmin)
        }
    }

    private suspend fun sendInvitation(email: String, isAdmin: Boolean) {
        val sendUserInvitationCaseIn = SendUserInvitationCaseIn(email, isAdmin)
        when (val result = sendInvitationUseCase.invoke(sendUserInvitationCaseIn)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> invitationSent.value = true
        }
    }

}
