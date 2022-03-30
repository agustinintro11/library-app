package com.example.libraryapp.data.service

import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.EditableBook
import com.example.libraryapp.data.model.PaginatedResponse
import retrofit2.Response
import retrofit2.http.*

interface BookService {
    @GET("books_service/books")
    suspend fun searchBooks(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Query("filter") filter: String?,
        @Query("page") page: Int,
        @Query("limit") size: Int
    ): List<Book>

    @DELETE("books_service/books/{isbn}")
    suspend fun deleteBook(
        @Path("isbn") isbn: String,
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
    ): Response<Unit>

    @POST("books_service/books")
    suspend fun addBook(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Body book: Book
    ): Book?

    @PUT("books_service/books/{isbn}")
    suspend fun editBook(
        @Path("isbn") isbn: String,
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String,
        @Body editBook: EditableBook
    ): Book?
}
