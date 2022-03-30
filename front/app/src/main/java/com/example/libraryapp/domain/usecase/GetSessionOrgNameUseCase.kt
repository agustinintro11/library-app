package com.example.libraryapp.domain.usecase

import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetSessionOrgNameUseCase(
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, String?>(dispatcher) {

    override suspend fun execute(params: String): String? {
        return sessionRepository.getCurrentOrgName()
    }
}
