package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Reservation
import com.example.libraryapp.data.model.ReservationCreate
import com.example.libraryapp.domain.dto.CreateReservationCaseIn
import com.example.libraryapp.domain.repository.ReservationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class CreateReservationUseCase(
    private val reservationRepository: ReservationRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CreateReservationCaseIn, Reservation?>(dispatcher) {
    override suspend fun execute(params: CreateReservationCaseIn): Reservation? {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val reservationCreate = ReservationCreate(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            Reservation(params.isbn, params.date)
        )

        return reservationRepository.createReservation(reservationCreate)

    }
}