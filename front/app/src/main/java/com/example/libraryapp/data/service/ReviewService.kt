package com.example.libraryapp.data.service

import com.example.libraryapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ReviewService {

    @POST("reviews_service/reviews")
    suspend fun createReview(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Body review: Review
    ): Response<ReviewOfBook?>

    @GET("reviews_service/reviews")
    suspend fun getBookReviews(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Query("isbn") isbn: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ReviewOfBook>?

    @DELETE("reviews_service/reviews/{id}")
    suspend fun deleteReview(
        @Path("id") id: Int,
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
    ): Response<Unit>
}
