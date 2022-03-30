package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.User
import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetSessionUserUseCase(
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, User?>(dispatcher) {

    override suspend fun execute(params: String): User? {
        return sessionRepository.getCurrentUser()
    }
}
