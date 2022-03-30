package com.example.libraryapp.ui.screens.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.data.model.ReservationOfUser
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.dto.GetActiveUserReservationsCaseIn
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.usecase.GetActiveReservationsUseCase
import com.example.libraryapp.domain.usecase.GetCatalogBooksUseCase

class ReservationsViewModel(private val getActiveReservationsUseCase: GetActiveReservationsUseCase) : ViewModel() {
    var activeReservations: LiveData<ProcessState<List<ReservationOfBook>?>> = MutableLiveData()

    fun loadResults(status: String, page: Int, limit: Int) {
        activeReservations =
            getActiveReservationsUseCase.invokeAsLiveData(GetActiveUserReservationsCaseIn(status, page, limit))
    }
}
