package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Invitation
import com.example.libraryapp.data.model.InvitationCreate
import com.example.libraryapp.data.model.Reservation
import com.example.libraryapp.data.model.ReservationCreate
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.dto.SendUserInvitationCaseIn
import com.example.libraryapp.domain.repository.OrganizationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import com.example.libraryapp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class SendUserInvitationUseCase(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<SendUserInvitationCaseIn, Response<Void>?>(dispatcher) {

    override suspend fun execute(params: SendUserInvitationCaseIn): Response<Void>? {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val invitationCreate = InvitationCreate(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            Invitation(params.email, params.isAdmin)
        )
        return userRepository.sendInvitation(invitationCreate)
    }
}
