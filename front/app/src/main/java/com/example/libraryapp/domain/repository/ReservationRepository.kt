package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.Reservation
import com.example.libraryapp.data.model.ReservationCreate
import com.example.libraryapp.data.model.ReservationSearch
import com.example.libraryapp.data.repository.reservation.ReservationRemoteSource

class ReservationRepository(
    private val reservationRemoteSource: ReservationRemoteSource
) {
    suspend fun createReservation(reservationCreate: ReservationCreate): Reservation? {
        return reservationRemoteSource.createReservation(reservationCreate)
    }

    suspend fun getUserReservations(apiKey:String, token: String, status: String, page: Int, limit: Int) =
        reservationRemoteSource.getUserReservations(apiKey, token, status, page, limit)

    suspend fun getBookReservations(reservationSearch: ReservationSearch) =
        reservationRemoteSource.getBookReservations(reservationSearch)

    suspend fun returnBook(apiKey: String, token: String, id: Int) =
        reservationRemoteSource.returnBook(apiKey, token, id)

}
