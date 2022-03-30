package com.example.libraryapp.ui.screens.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.*
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.CreateReservationCaseIn
import com.example.libraryapp.domain.dto.CreateReviewCaseIn
import com.example.libraryapp.domain.dto.GetBookReviewsCaseIn
import com.example.libraryapp.domain.usecase.*
import kotlinx.coroutines.launch

class BookViewModel(
    private val getSessionUserUseCase: GetSessionUserUseCase,
    private val getBookReviewsUseCase: GetBookReviewsUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val createReservationUseCase: CreateReservationUseCase
) : ViewModel() {
    var error = MutableLiveData<String>()
    var createdReservation = MutableLiveData<Reservation>()
    var createdReview = MutableLiveData<ReviewOfBook>()
    var deletedReview = MutableLiveData<ReviewOfBook>()
    var orgApiKey = MutableLiveData<OrgApiKey>()
    var sessionUser = MutableLiveData<User>()

    var errorDate = MutableLiveData<String>()

    var bookReviews: LiveData<ProcessState<List<ReviewOfBook>?>> = MutableLiveData()

    fun buttonClick(
        date: String,
        isbn: String,
    ) {
        var error = false

        if (date.isEmpty()) {
            errorDate.value = "Ingrese una fecha"
            error = true
        }

        if (error)
            return

        viewModelScope.launch {
            createReservation(isbn, date)
        }
    }

    private suspend fun createReservation(isbn: String, date: String) {
        val createReservationCaseIn = CreateReservationCaseIn(isbn, date)
        when (val result = createReservationUseCase.invoke(createReservationCaseIn)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> createdReservation.value = result.value!!
        }

    }

    fun getSessionUser() {
        viewModelScope.launch {
            when (val result = getSessionUserUseCase.invoke("")) {
                is Result.Error -> error.value = result.message ?: "Error inesperado"
                is Result.Success -> sessionUser.value = result.value!!
            }
        }
    }


    fun loadResults(isbn: String, page: Int, size: Int) {
        bookReviews =
            getBookReviewsUseCase.invokeAsLiveData(GetBookReviewsCaseIn(isbn, page, size))
    }

    fun deleteReview(review: ReviewOfBook) {
        viewModelScope.launch {
            when (val result = deleteReviewUseCase.invoke(review.id)) {
                is Result.Error -> error.value = result.message ?: "Error inesperado"
                is Result.Success -> deletedReview.value = review
            }
        }
    }

    fun createReview(rating: Float, description: String, isbn: String) {
        viewModelScope.launch {
            val createReviewCaseIn = CreateReviewCaseIn(rating, description, isbn)
            when (val result = createReviewUseCase.invoke(createReviewCaseIn)) {
                is Result.Error -> error.value = result.message ?: "Error inesperado"
                is Result.Success -> createdReview.value = result.value.body()
            }
        }
    }
}
