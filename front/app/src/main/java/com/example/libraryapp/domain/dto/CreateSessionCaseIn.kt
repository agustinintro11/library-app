package com.example.libraryapp.domain.dto

import com.example.libraryapp.data.model.Credentials
import com.example.libraryapp.data.model.Organization

class CreateSessionCaseIn(
    val organization: Organization,
    val credentials: Credentials
)
