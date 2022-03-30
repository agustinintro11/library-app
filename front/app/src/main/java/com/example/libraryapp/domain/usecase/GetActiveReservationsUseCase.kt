package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.data.model.ReservationOfUser
import com.example.libraryapp.domain.dto.GetActiveUserReservationsCaseIn
import com.example.libraryapp.domain.repository.ReservationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetActiveReservationsUseCase(
    private val reservationRepository: ReservationRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetActiveUserReservationsCaseIn, List<ReservationOfBook>?>(dispatcher) {
    override suspend fun execute(params: GetActiveUserReservationsCaseIn): List<ReservationOfBook> {
        val sessionToken = sessionRepository.getSessionToken()!!
        val apiKey = sessionRepository.getApiKey()!!
        return reservationRepository.getUserReservations(
            apiKey,
            sessionToken,
            params.status,
            params.page,
            params.limit
        )!!
    }
}
