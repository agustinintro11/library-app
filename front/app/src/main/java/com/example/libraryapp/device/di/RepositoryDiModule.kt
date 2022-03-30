package com.example.libraryapp.device.di

import android.content.Context
import com.example.libraryapp.BuildConfig
import com.example.libraryapp.data.repository.book.BookRemoteSource
import com.example.libraryapp.data.repository.organization.OrganizationRemoteSource
import com.example.libraryapp.data.repository.reservation.ReservationRemoteSource
import com.example.libraryapp.data.repository.review.ReviewRemoteSource
import com.example.libraryapp.data.repository.session.SessionLocalSource
import com.example.libraryapp.data.repository.session.SessionRemoteSource
import com.example.libraryapp.data.repository.store.SharedPreferencesStore
import com.example.libraryapp.data.repository.store.SharedPreferencesStoreImpl
import com.example.libraryapp.data.repository.user.UserRemoteSource
import com.example.libraryapp.domain.repository.*
import com.google.gson.Gson
import org.koin.dsl.module

object RepositoryDiModuleProvider {
    val stores = module {
        single<SharedPreferencesStore> {
            SharedPreferencesStoreImpl(
                get<Context>().getSharedPreferences(
                    BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        }

    }
    val sources = module {
        single { SessionLocalSource(get(), Gson()) }
        single { SessionRemoteSource(get()) }
        single { OrganizationRemoteSource(get()) }
        single { UserRemoteSource(get()) }
        single { BookRemoteSource(get()) }
        single { ReservationRemoteSource(get()) }
        single { ReviewRemoteSource(get()) }
    }
    val repositories = module {
        single { SessionRepository(get(), get()) }
        single { OrganizationRepository(get()) }
        single { UserRepository(get()) }
        single { BookRepository(get()) }
        single { ReservationRepository(get()) }
        single { ReviewRepository(get()) }

    }
}
