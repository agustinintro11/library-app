package com.example.libraryapp.data.model

class BookSearch(
    val apiKey: String,
    val token: String,
    val filter: String?,
    val page: Int,
    val size: Int
)
