package com.example.libraryapp.data.model

class ReservationSearch(
    val apiKey: String,
    val token: String,
    val isbn: String?,
    val username: String?,
    val page: Int,
    val limit: Int
)
