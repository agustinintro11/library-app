package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.BookDelete
import com.example.libraryapp.data.model.ReviewDelete
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.ReviewRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, Response<Unit>>(dispatcher) {

    override suspend fun execute(params: Int): Response<Unit> {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val reviewDelete = ReviewDelete(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            params
        )
        return reviewRepository.deleteReview(reviewDelete)
    }
}
