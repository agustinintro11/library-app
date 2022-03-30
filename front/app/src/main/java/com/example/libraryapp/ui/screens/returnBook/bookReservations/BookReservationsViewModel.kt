package com.example.libraryapp.ui.screens.returnBook.bookReservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.ReservationOfBook
import com.example.libraryapp.data.model.ReviewOfBook
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.CreateReviewCaseIn
import com.example.libraryapp.domain.dto.GetBookReservationCaseIn
import com.example.libraryapp.domain.usecase.GetReservationsOfBookUseCase
import com.example.libraryapp.domain.usecase.ReturnReservationOfBookUseCase
import kotlinx.coroutines.launch

class BookReservationsViewModel(
    private val getReservationsOfBookUseCase: GetReservationsOfBookUseCase,
    private val returnReservationOfBookUseCase: ReturnReservationOfBookUseCase,
) : ViewModel() {
    var reservationsOfBooks: LiveData<ProcessState<List<ReservationOfBook>?>> = MutableLiveData()
    var returnedBook = MutableLiveData<ReservationOfBook>()
    var error = MutableLiveData<String>()

    fun loadResults(isbn: String, username: String?, page: Int, size: Int) {
        reservationsOfBooks =
            getReservationsOfBookUseCase.invokeAsLiveData(
                GetBookReservationCaseIn(
                    isbn,
                    username,
                    page,
                    size
                )
            )
    }

    fun returnBook(id: Int) {
        viewModelScope.launch {
            when (val result = returnReservationOfBookUseCase.invoke(id)) {
                is Result.Error -> error.value = result.message ?: "Error inesperado"
                is Result.Success -> returnedBook.value = result.value.body()
            }
        }
    }
}
