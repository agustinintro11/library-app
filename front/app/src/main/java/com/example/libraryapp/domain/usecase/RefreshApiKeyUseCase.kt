package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.domain.repository.OrganizationRepository
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class RefreshApiKeyUseCase(
    private val organizationRepository: OrganizationRepository,
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, OrgApiKey?>(dispatcher) {

    override suspend fun execute(params: String): OrgApiKey? {
        val apiKey = sessionRepository.getApiKey()!!
        val sessionToken = sessionRepository.getSessionToken()!!

        val refreshedApiKey = organizationRepository.refreshOrganizationApiKey(apiKey, sessionToken)
        if(refreshedApiKey != null){
            sessionRepository.setApiKey(refreshedApiKey)
        }
        return refreshedApiKey
    }
}
