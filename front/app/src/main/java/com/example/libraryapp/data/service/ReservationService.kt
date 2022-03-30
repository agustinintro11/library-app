package com.example.libraryapp.data.service

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.Reservation
import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.data.model.ReservationOfUser
import retrofit2.Response
import retrofit2.http.*

interface ReservationService {
    @POST("reservations_service/reservations")
    suspend fun createReservation(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Body reservation: Reservation
    ): Reservation?

    //TODO: This endpoint is not working with new changes. Fix when new requirement about user
    // reservations is implemented
    @GET("reservations_service/reservations/me")
    suspend fun getUserReservations(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Query("status") status: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ReservationOfBook>?


    @GET("reservations_service/reservations")
    suspend fun getBookReservations(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Query("isbn") isbn: String?,
        @Query("username") username: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ReservationOfBook>

    @PATCH("reservations_service/reservations/{id}/return")
    suspend fun returnBook(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ReservationOfBook?>
}
