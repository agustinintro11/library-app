package com.example.libraryapp.data.service

import com.example.libraryapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("users_service/users/me")
    suspend fun getMe(
        @Header("Authorization") token: String,
        @Header("api-key") orgApiKey: String,
    ): User?

    @GET("users_service/users/reservations")
    suspend fun getUserReservations(
        @Header("Authorization") token: String,
        @Query("filter") active: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PaginatedResponse<ReservationOfUser>?

    @POST("users_service/sessions")
    suspend fun createSession(
        @Header("api-key") orgApiKey: String,
        @Body credentials: Credentials
    ): Response<Session?>

    @POST("users_service/users")
    suspend fun createAdmin(@Body adminCreate: AdminCreate): AdminAndOrgCreated?

    @POST("users_service/invitations")
    suspend fun sendInvitation(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Body invitation: Invitation
    ): Response<Void>?
}
