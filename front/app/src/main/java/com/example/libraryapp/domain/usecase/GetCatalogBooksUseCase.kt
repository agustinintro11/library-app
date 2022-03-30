package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.BookSearch
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetCatalogBooksUseCase(
    private val bookRepository: BookRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetCatalogBooksCaseIn, List<Book>?>(dispatcher) {
    override suspend fun execute(params: GetCatalogBooksCaseIn): List<Book> {
        val apiKey = sessionRepository.getApiKey()!!
        val sessionToken = sessionRepository.getSessionToken()!!
        val bookSearch = BookSearch(apiKey, sessionToken, params.filter, params.page, params.limit)
        return bookRepository.searchBooks(bookSearch)
    }

}
