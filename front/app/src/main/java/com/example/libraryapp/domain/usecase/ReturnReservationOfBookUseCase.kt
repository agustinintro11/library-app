package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.domain.repository.ReservationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class ReturnReservationOfBookUseCase(
    private val reservationRepository: ReservationRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, Response<ReservationOfBook?>>(dispatcher) {
    override suspend fun execute(params: Int): Response<ReservationOfBook?> {
        val apiKey = sessionRepository.getApiKey()!!
        val sessionToken = sessionRepository.getSessionToken()!!
        return reservationRepository.returnBook(apiKey, sessionToken, params)
    }

}
