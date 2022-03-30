package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.*
import com.example.libraryapp.domain.dto.CreateReviewCaseIn
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.ReviewRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class CreateReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CreateReviewCaseIn, Response<ReviewOfBook?>>(dispatcher) {

    override suspend fun execute(params: CreateReviewCaseIn): Response<ReviewOfBook?> {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val reviewCreate = ReviewCreate(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            Review(params.rating,params.description, params.isbn)
        )
        return reviewRepository.createReview(reviewCreate)
    }
}
