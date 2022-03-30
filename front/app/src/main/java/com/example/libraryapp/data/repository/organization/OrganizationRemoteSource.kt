package com.example.libraryapp.data.repository.organization

import com.example.libraryapp.data.model.InvitationCreate
import com.example.libraryapp.data.model.AdminCreate
import com.example.libraryapp.data.service.OrganizationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrganizationRemoteSource(private val organizationService: OrganizationService) {
    suspend fun getOrganizationApiKey(organizationName: String) = withContext(
        Dispatchers.IO
    ) {
        organizationService.getOrganizationApiKey(organizationName)
    }

    suspend fun refreshApiKey(apiKey: String, sessionToken: String) = withContext(
        Dispatchers.IO
    ) {
        organizationService.refreshApiKey(apiKey, sessionToken)
    }
}
