package com.example.libraryapp.device.di

import com.example.libraryapp.ui.common.SessionViewModel
import com.example.libraryapp.ui.screens.apikeyManagement.ApikeyManagementViewModel
import com.example.libraryapp.ui.screens.auth.LoginViewModel
import com.example.libraryapp.ui.screens.auth.SignupViewModel
import com.example.libraryapp.ui.screens.auth.SplashViewModel
import com.example.libraryapp.ui.screens.book.BookViewModel
import com.example.libraryapp.ui.screens.catalog.CatalogViewModel
import com.example.libraryapp.ui.screens.dashboard.DashboardViewModel
import com.example.libraryapp.ui.screens.inviteUser.InviteUserViewModel
import com.example.libraryapp.ui.screens.manageBooks.ManageBooksViewModel
import com.example.libraryapp.ui.screens.manageBooks.addBook.AddBookViewModel
import com.example.libraryapp.ui.screens.manageBooks.editBook.EditBookViewModel
import com.example.libraryapp.ui.screens.reservations.ReservationsViewModel
import com.example.libraryapp.ui.screens.returnBook.ReturnBookViewModel
import com.example.libraryapp.ui.screens.returnBook.bookReservations.BookReservationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelDiModule {
    val viewModels = module {

        viewModel { LoginViewModel(get(), get()) }
        viewModel { SignupViewModel(get()) }
        viewModel { SplashViewModel() }
        viewModel { BookViewModel(get(), get(), get(), get(), get()) }
        viewModel { CatalogViewModel(get()) }
        viewModel { DashboardViewModel() }
        viewModel { ReservationsViewModel(get()) }
        viewModel { SessionViewModel(get(), get(), get()) }
        viewModel { ApikeyManagementViewModel(get(), get()) }
        viewModel { InviteUserViewModel(get()) }
        viewModel { ManageBooksViewModel(get(), get()) }
        viewModel { AddBookViewModel(get()) }
        viewModel { EditBookViewModel(get()) }
        viewModel { ReturnBookViewModel(get()) }
        viewModel { BookReservationsViewModel(get(),get()) }
    }
}
