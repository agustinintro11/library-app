package com.example.libraryapp.data.repository.review

import com.example.libraryapp.data.model.ReviewCreate
import com.example.libraryapp.data.model.ReviewDelete
import com.example.libraryapp.data.model.ReviewsSearch
import com.example.libraryapp.data.service.ReviewService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRemoteSource(
    private val reviewService: ReviewService
) {
    suspend fun createReview(reviewCreate: ReviewCreate) = withContext(
        Dispatchers.IO
    ) {
        reviewService.createReview(reviewCreate.apiKey, reviewCreate.token, reviewCreate.review)
    }

    suspend fun getBookReviews(reviewsSearch: ReviewsSearch) =
        withContext(Dispatchers.IO) {
            reviewService.getBookReviews(
                reviewsSearch.apiKey,
                reviewsSearch.token,
                reviewsSearch.isbn,
                reviewsSearch.page,
                reviewsSearch.limit
            )
        }

    suspend fun deleteReview(reviewDelete: ReviewDelete) =
        withContext(Dispatchers.IO) {
            reviewService.deleteReview(reviewDelete.id, reviewDelete.apiKey, reviewDelete.token)
        }
}
