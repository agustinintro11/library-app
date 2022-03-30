package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.AdminCreate
import com.example.libraryapp.data.model.InvitationCreate
import com.example.libraryapp.data.repository.user.UserRemoteSource
import retrofit2.Response

class UserRepository(
    private val userRemoteSource: UserRemoteSource
) {
    suspend fun getMe(token: String, apikey: String) =
        userRemoteSource.getMe(token, apikey)

    suspend fun createAdmin(adminCreate: AdminCreate) = userRemoteSource.createAdmin(adminCreate)

    suspend fun sendInvitation(invitationCreate: InvitationCreate): Response<Void>? {
        return userRemoteSource.sendInvitation(invitationCreate)
    }

}
