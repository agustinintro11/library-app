package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.*
import com.example.libraryapp.data.repository.book.BookRemoteSource

class BookRepository(private val bookRemoteSource: BookRemoteSource) {
    suspend fun searchBooks(bookSearch: BookSearch) = bookRemoteSource.searchBooks(bookSearch)

    suspend fun deleteBook(bookDelete: BookDelete) = bookRemoteSource.deleteBook(bookDelete)

    suspend fun addBook(bookAdd: BookAdd) = bookRemoteSource.addBook(bookAdd)

    suspend fun editBook(bookEdit: BookEdit) = bookRemoteSource.editBook(bookEdit)
}
