package com.example.libraryapp.data.repository.session

import com.example.libraryapp.data.model.Credentials
import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRemoteSource(private val userService: UserService) {
    suspend fun createSession(orgApiKey: OrgApiKey, credentials: Credentials) = withContext(
        Dispatchers.IO
    ) {
        userService.createSession(orgApiKey.apiKey, credentials)
    }
}
