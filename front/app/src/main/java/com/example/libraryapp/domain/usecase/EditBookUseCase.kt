package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.BookEdit
import com.example.libraryapp.data.model.EditableBook
import com.example.libraryapp.domain.dto.EditBookCaseIn
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class EditBookUseCase(
    private val bookRepository: BookRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<EditBookCaseIn, Book?>(dispatcher) {

    override suspend fun execute(params: EditBookCaseIn): Book? {
        if (!sessionRepository.isUserLogged())
            throw SecurityException("User should be logged in")

        val editableBook =
            EditableBook(params.isbn, params.title, params.authors, params.year, params.quantity)

        val bookEdit = BookEdit(
            sessionRepository.getApiKey()!!,
            sessionRepository.getSessionToken()!!,
            params.currentIsbn,
            editableBook
        )
        return bookRepository.editBook(bookEdit)
    }
}
