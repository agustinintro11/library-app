package com.example.libraryapp.data.repository.reservation

import com.example.libraryapp.data.model.BookSearch
import com.example.libraryapp.data.model.ReservationCreate
import com.example.libraryapp.data.model.ReservationSearch
import com.example.libraryapp.data.service.ReservationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRemoteSource(
    private val reservationService: ReservationService
) {
    suspend fun createReservation(reservationCreate: ReservationCreate) = withContext(
        Dispatchers.IO
    ) {
        reservationService.createReservation(reservationCreate.apiKey, reservationCreate.token, reservationCreate.reservation)
    }

    suspend fun getUserReservations(apiKey:String, token: String, status: String, page: Int, limit: Int) =
        withContext( Dispatchers.IO ) {
            reservationService.getUserReservations(apiKey, token, status, page, limit)
        }

    suspend fun getBookReservations(reservationSearch: ReservationSearch) = withContext(Dispatchers.IO) {
        reservationService.getBookReservations(
            reservationSearch.apiKey,
            reservationSearch.token,
            reservationSearch.isbn,
            reservationSearch.username,
            reservationSearch.page,
            reservationSearch.limit
        )
    }

    suspend fun returnBook(apiKey: String, token: String, id: Int) =
        withContext( Dispatchers.IO ) {
            reservationService.returnBook(apiKey, token, id)
        }

}
