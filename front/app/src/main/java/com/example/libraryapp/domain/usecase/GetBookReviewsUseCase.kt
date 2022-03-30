package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.*
import com.example.libraryapp.domain.dto.GetBookReviewsCaseIn
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.ReviewRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetBookReviewsUseCase(
    private val reviewRepository: ReviewRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetBookReviewsCaseIn, List<ReviewOfBook>?>(dispatcher) {
    override suspend fun execute(params: GetBookReviewsCaseIn): List<ReviewOfBook>? {
        val apiKey = sessionRepository.getApiKey()!!
        val sessionToken = sessionRepository.getSessionToken()!!
        val reviewsSearch = ReviewsSearch(apiKey, sessionToken, params.isbn, params.page, params.limit)
        return reviewRepository.getBookReviews(reviewsSearch)
    }

}
