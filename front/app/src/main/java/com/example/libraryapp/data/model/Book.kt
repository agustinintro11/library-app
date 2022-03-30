package com.example.libraryapp.data.model

import java.io.Serializable

class Book(
    val isbn: String,
    val title: String,
    val authors: String,
    val year: Int,
    val quantity: Int,
) : Serializable
