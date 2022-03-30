package com.example.libraryapp.domain.dto

import com.example.libraryapp.data.model.User

class CreateReviewCaseIn(
    val rating: Float,
    val description: String,
    val isbn: String,
)
