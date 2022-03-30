package com.example.libraryapp.data.service

import com.example.libraryapp.data.model.Invitation
import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Organization
import com.example.libraryapp.data.model.AdminCreate
import retrofit2.Response
import retrofit2.http.*

interface OrganizationService {

    @GET("organizations_service/organizations/{name}/apiKey")
    suspend fun getOrganizationApiKey(@Path("name") organizationName: String): OrgApiKey?

    @POST("organizations_service/organizations/renew")
    suspend fun refreshApiKey(
        @Header("api-key") apiKey: String,
        @Header("Authorization") token: String
    ): OrgApiKey?
}
