package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.Credentials
import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Organization
import com.example.libraryapp.data.model.User
import com.example.libraryapp.data.repository.session.SessionLocalSource
import com.example.libraryapp.data.repository.session.SessionRemoteSource

class SessionRepository(
    private val sessionLocalSource: SessionLocalSource,
    private val sessionRemoteSource: SessionRemoteSource
) {
    suspend fun getSessionToken() = sessionLocalSource.getSessionToken()
    suspend fun getApiKey() = sessionLocalSource.getApiKey()
    suspend fun setApiKey(apiKey: OrgApiKey) = sessionLocalSource.setApiKey(apiKey.apiKey)
    suspend fun isUserLogged(): Boolean {
        return !getSessionToken().isNullOrBlank()
    }

    suspend fun create(user: User, token: String, organization: Organization) =
        sessionLocalSource.setSession(user, token, organization)

    suspend fun getCurrentUser() = sessionLocalSource.getSessionUser()

    suspend fun getCurrentOrgName() = sessionLocalSource.getSessionOrg()

    suspend fun createSession(orgApiKey: OrgApiKey, credentials: Credentials) =
        sessionRemoteSource.createSession(orgApiKey, credentials)

    suspend fun destroy() {
        sessionLocalSource.destroySession()
    }

}
