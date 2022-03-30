package com.example.libraryapp.ui.screens.manageBooks.addBook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.usecase.AddBookUseCase
import kotlinx.coroutines.launch

class AddBookViewModel(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    var addedBook = MutableLiveData<Book>()
    var error = MutableLiveData<String>()

    var errorTitle = MutableLiveData<String>()
    var errorAuthors = MutableLiveData<String>()
    var errorISBN = MutableLiveData<String>()
    var errorYear = MutableLiveData<String>()
    var errorQuantity = MutableLiveData<String>()

    private suspend fun addBook(book: Book) {
        when (val result = addBookUseCase.invoke(book)) {
            is Result.Error -> error.value = result.message ?: "Error inesperado"
            is Result.Success -> addedBook.value = result.value!!
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
        } else if(year.toInt() <= 0) {
            errorYear.value = "Ingrese un año válido"
            error = true
        }
        if (quantity.isEmpty()) {
            errorQuantity.value = "Ingrese la cantidad de ejemplares"
            error = true
        } else if(quantity.toInt() <= 0) {
            errorYear.value = "Ingrese una cantidad válida"
            error = true
        }
        if (error)
            return

        viewModelScope.launch {
            addBook(Book(isbn,title,authors,year.toInt(),quantity.toInt()))
        }
    }
}
