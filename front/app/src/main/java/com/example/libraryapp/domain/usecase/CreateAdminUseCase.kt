package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.AdminAndOrgCreated
import com.example.libraryapp.data.model.AdminCreate
import com.example.libraryapp.domain.dto.CreateAdminCaseIn
import com.example.libraryapp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class CreateAdminUseCase(
    private val userRepository: UserRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CreateAdminCaseIn, AdminAndOrgCreated?>(dispatcher) {
    override suspend fun execute(params: CreateAdminCaseIn): AdminAndOrgCreated? {

        val adminCreate = AdminCreate(
            params.name,
            params.organizationName,
            params.email,
            params.password
        )

        return userRepository.createAdmin(adminCreate)

    }
}
