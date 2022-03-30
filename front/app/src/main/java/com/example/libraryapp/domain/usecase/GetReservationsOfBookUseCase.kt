package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.BookSearch
import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.data.model.ReservationSearch
import com.example.libraryapp.domain.dto.GetBookReservationCaseIn
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.ReservationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetReservationsOfBookUseCase(
    private val reservationRepository: ReservationRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetBookReservationCaseIn, List<ReservationOfBook>?>(dispatcher) {
    override suspend fun execute(params: GetBookReservationCaseIn): List<ReservationOfBook> {
        val apiKey = sessionRepository.getApiKey()!!
        val sessionToken = sessionRepository.getSessionToken()!!
        val reservationSearch = ReservationSearch(apiKey, sessionToken, params.isbn, params.username, params.page, params.limit)
        return reservationRepository.getBookReservations(reservationSearch)
    }

}
