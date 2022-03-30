package com.example.libraryapp.ui.screens.manageBooks.editBook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.EditBookCaseIn
import com.example.libraryapp.domain.usecase.EditBookUseCase
import kotlinx.coroutines.launch

class EditBookViewModel(
    private val editBookUseCase: EditBookUseCase
) : ViewModel() {

    var modifiedBook = MutableLiveData<Book>()
    var error = MutableLiveData<String>()
    var currentBook = MutableLiveData<Book>()
    var isModified = MutableLiveData<Boolean>()

    var errorTitle = MutableLiveData<String>()
    var errorAuthors = MutableLiveData<String>()
    var errorISBN = MutableLiveData<String>()
    var errorYear = MutableLiveData<String>()
    var errorQuantity = MutableLiveData<String>()

    private suspend fun editBook(book: EditBookCaseIn) {
        when (val result = editBookUseCase.invoke(book)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> modifiedBook.value = result.value!!
        }
    }

    fun buttonClick(title: String, isbn: String, authors: String, year: String, quantity: String) {
        var error = false

        if (title.isEmpty()) {
            errorTitle.value = "Ingrese un título"
            error = true
        }
        if (isbn.isEmpty()) {
            errorISBN.value = "Ingrese un ISBN"
            error = true
        }
        if (authors.isEmpty()) {
            errorAuthors.value = "Ingrese autores"
            error = true
        }
        if (year.isEmpty()) {
            errorYear.value = "Ingrese un año"
            error = true
        } else if (year.toInt() <= 0) {
            errorYear.value = "Ingrese un año válido"
            error = true
        }
        if (quantity.isEmpty()) {
            errorQuantity.value = "Ingrese la cantidad de ejemplares"
            error = true
        } else if (quantity.toInt() <= 0) {
            errorYear.value = "Ingrese una cantidad válida"
            error = true
        }
        if (error)
            return

        viewModelScope.launch {

            val current = currentBook.value!!
            val newIsbn = if (current.isbn != isbn) isbn else null
            val newTitle = if (current.title != title) title else null
            val newAuthors = if (current.authors != authors) authors else null
            val newYear = if (current.year != year.toInt()) year.toInt() else null
            val newQuantity = if (current.quantity != quantity.toInt()) quantity.toInt() else null

            editBook(
                EditBookCaseIn(
                    currentBook.value!!.isbn,
                    newIsbn,
                    newTitle,
                    newAuthors,
                    newYear,
                    newQuantity
                )
            )
        }
    }
}
