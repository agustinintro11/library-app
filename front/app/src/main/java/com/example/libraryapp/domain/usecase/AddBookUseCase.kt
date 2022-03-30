package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.BookAdd
import com.example.libraryapp.data.model.BookDelete
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class AddBookUseCase(
    private val bookRepository: BookRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Book, Book?>(dispatcher) {

    override suspend fun execute(params: Book): Book? {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val bookAdd = BookAdd(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            params
        )
        return bookRepository.addBook(bookAdd)
    }
}
