package com.example.libraryapp.device.di

import com.example.libraryapp.domain.usecase.*
import org.koin.dsl.module

object UseCaseDiModule {
    val useCases = module {
        factory { CreateSessionUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetOrganizationApiKeyUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { CreateAdminUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { CreateReservationUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetCatalogBooksUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetActiveReservationsUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetSessionUserUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { DestroySessionUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { GetSessionOrgNameUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { GetCurrentApiKeyUseCase(get(), get(DEFAULT_DISPATCHER)) }
        factory { RefreshApiKeyUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { SendUserInvitationUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { DeleteBookUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { AddBookUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { EditBookUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetBookReviewsUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { DeleteReviewUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { CreateReviewUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { GetReservationsOfBookUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
        factory { ReturnReservationOfBookUseCase(get(), get(), get(DEFAULT_DISPATCHER)) }
    }
}
