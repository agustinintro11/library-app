package com.example.libraryapp.ui.screens.manageBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.Result
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.usecase.DeleteBookUseCase
import com.example.libraryapp.domain.usecase.GetCatalogBooksUseCase
import kotlinx.coroutines.launch

class ManageBooksViewModel(
    private val getCatalogBooksUseCase: GetCatalogBooksUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {
    var manageBooksBooks: LiveData<ProcessState<List<Book>?>> = MutableLiveData()
    var deletedBook = MutableLiveData<Book>()
    var error = MutableLiveData<String>()

    fun loadResults(filter: String?, page: Int, size: Int) {
        manageBooksBooks =
            getCatalogBooksUseCase.invokeAsLiveData(GetCatalogBooksCaseIn(filter, page, size))
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            when (val result = deleteBookUseCase.invoke(book.isbn)) {
                is Result.Error -> error.value = result.message ?: "Error inesperado"
                is Result.Success -> deletedBook.value = book
            }
        }
    }
}
