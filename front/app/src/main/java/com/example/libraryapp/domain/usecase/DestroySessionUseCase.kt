package com.example.libraryapp.domain.usecase

import com.example.libraryapp.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher

class DestroySessionUseCase(
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(params: Unit) {
        sessionRepository.destroy()
    }
}
