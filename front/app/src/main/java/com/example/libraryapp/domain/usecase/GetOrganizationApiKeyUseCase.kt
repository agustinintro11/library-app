package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.domain.repository.OrganizationRepository
import kotlinx.coroutines.CoroutineDispatcher


class GetOrganizationApiKeyUseCase(
    private val organizationRepository: OrganizationRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, OrgApiKey?>(dispatcher) {

    override suspend fun execute(params: String): OrgApiKey? {
        return organizationRepository.getOrganizationApiKey(params)
    }
}