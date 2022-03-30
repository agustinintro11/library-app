package com.example.libraryapp.ui.screens.returnBook

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

class ReturnBookViewModel(
    private val getCatalogBooksUseCase: GetCatalogBooksUseCase,
) : ViewModel() {
    var returnBookBooks: LiveData<ProcessState<List<Book>?>> = MutableLiveData()
    var error = MutableLiveData<String>()

    fun loadResults(filter: String?, page: Int, size: Int) {
        returnBookBooks =
            getCatalogBooksUseCase.invokeAsLiveData(GetCatalogBooksCaseIn(filter, page, size))
    }
}
