package com.example.libraryapp.domain.repository

import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.repository.organization.OrganizationRemoteSource

class OrganizationRepository(
    private val organizationRemoteSource: OrganizationRemoteSource
) {
    suspend fun getOrganizationApiKey(organizationName: String) =
        organizationRemoteSource.getOrganizationApiKey(organizationName)

    suspend fun refreshOrganizationApiKey(apiKey: String, sessionToken: String): OrgApiKey? {
        return organizationRemoteSource.refreshApiKey(apiKey, sessionToken)
    }

}
