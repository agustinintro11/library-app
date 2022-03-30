package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Session
import com.example.libraryapp.domain.dto.CreateSessionCaseIn
import com.example.libraryapp.domain.repository.SessionRepository
import com.example.libraryapp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class CreateSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CreateSessionCaseIn, Session?>(dispatcher) {
    override suspend fun execute(params: CreateSessionCaseIn): Session? {

        val session = sessionRepository.createSession(
            OrgApiKey(params.organization.apiKey),
            params.credentials
        )
        val token = session.headers()["Authorization"]

        if (token != null) {
            val user = userRepository.getMe(token, params.organization.apiKey)!!
            sessionRepository.create(user, token, params.organization)
            return Session(user, token)
        }

        return session.body()
    }
}
