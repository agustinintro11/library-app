package com.example.libraryapp.data.repository.book

import com.example.libraryapp.data.model.BookAdd
import com.example.libraryapp.data.model.BookDelete
import com.example.libraryapp.data.model.BookEdit
import com.example.libraryapp.data.model.BookSearch
import com.example.libraryapp.data.service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRemoteSource(private val bookService: BookService) {
    suspend fun searchBooks(bookSearch: BookSearch) = withContext(Dispatchers.IO) {
        bookService.searchBooks(
            bookSearch.apiKey,
            bookSearch.token,
            bookSearch.filter,
            bookSearch.page,
            bookSearch.size
        )
    }

    suspend fun deleteBook(bookDelete: BookDelete) = withContext(Dispatchers.IO) {
        bookService.deleteBook(bookDelete.isbn, bookDelete.apiKey, bookDelete.token)
    }

    suspend fun addBook(bookAdd: BookAdd) = withContext(Dispatchers.IO) {
        bookService.addBook(bookAdd.apiKey, bookAdd.token, bookAdd.book)
    }

    suspend fun editBook(bookEdit: BookEdit) = withContext(Dispatchers.IO) {
        bookService.editBook(bookEdit.isbn,bookEdit.apiKey, bookEdit.token, bookEdit.editBook)
    }
}
