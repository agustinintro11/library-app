package com.example.libraryapp.data.repository.user

import com.example.libraryapp.data.model.AdminCreate
import com.example.libraryapp.data.model.InvitationCreate
import com.example.libraryapp.data.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRemoteSource(private val userService: UserService) {
    suspend fun getMe(token: String, apikey: String) = withContext(
        Dispatchers.IO
    ) {
        userService.getMe(token, apikey)
    }

    suspend fun createAdmin(adminCreate: AdminCreate) = withContext(
        Dispatchers.IO
    ) {
        userService.createAdmin(adminCreate)
    }

    suspend fun sendInvitation(invitationCreate: InvitationCreate) = withContext(
        Dispatchers.IO
    ) {
        userService.sendInvitation(
            invitationCreate.apiKey,
            invitationCreate.token,
            invitationCreate.invitation
        )
    }
}
