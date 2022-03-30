package com.example.libraryapp.domain.dto

class EditBookCaseIn(
    val currentIsbn: String,
    val isbn: String?,
    val title: String?,
    val authors: String?,
    val year: Int?,
    val quantity: Int?,
)
