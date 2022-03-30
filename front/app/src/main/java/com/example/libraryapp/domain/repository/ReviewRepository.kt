package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.*
import com.example.libraryapp.data.repository.book.BookRemoteSource
import com.example.libraryapp.data.repository.review.ReviewRemoteSource

class ReviewRepository(private val reviewRemoteSource: ReviewRemoteSource) {
    suspend fun getBookReviews(reviewsSearch: ReviewsSearch) = reviewRemoteSource.getBookReviews(reviewsSearch)

    suspend fun deleteReview(reviewDelete: ReviewDelete) = reviewRemoteSource.deleteReview(reviewDelete)

    suspend fun createReview(reviewCreate: ReviewCreate) = reviewRemoteSource.createReview(reviewCreate)

}
