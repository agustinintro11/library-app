package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.BookDelete
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class DeleteBookUseCase(
    private val bookRepository: BookRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, Response<Unit>>(dispatcher) {

    override suspend fun execute(params: String): Response<Unit> {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val bookDelete = BookDelete(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            params
        )
        return bookRepository.deleteBook(bookDelete)
    }
}
