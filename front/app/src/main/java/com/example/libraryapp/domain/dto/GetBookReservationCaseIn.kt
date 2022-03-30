package com.example.libraryapp.domain.dto

class GetBookReservationCaseIn(
    val isbn: String?,
    val username: String?,
    val page: Int,
    val limit: Int
)
