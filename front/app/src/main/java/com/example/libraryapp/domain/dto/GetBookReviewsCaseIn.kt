package com.example.libraryapp.domain.dto

class GetBookReviewsCaseIn(
    val isbn: String,
    val page: Int,
    val limit: Int
)
