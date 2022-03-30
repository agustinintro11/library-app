package com.example.libraryapp.device.di

import com.example.libraryapp.BuildConfig
import com.example.libraryapp.data.model.Review
import com.example.libraryapp.data.service.*
import org.koin.dsl.module
import retrofit2.Retrofit

object NetworkDiModule {
    val network = module {
        single { get<Retrofit>().create(OrganizationService::class.java) }
        single { get<Retrofit>().create(UserService::class.java) }
        single { get<Retrofit>().create(BookService::class.java) }
        single { get<Retrofit>().create(ReservationService::class.java) }
        single { get<Retrofit>().create(ReviewService::class.java) }
        single { NetworkLayerCreator.createRetrofitInstance(BuildConfig.SERVER_URL) }
    }
}
