package com.example.libraryapp.ui.screens.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.device.ProcessState
import com.example.libraryapp.domain.dto.GetCatalogBooksCaseIn
import com.example.libraryapp.domain.usecase.GetCatalogBooksUseCase

class CatalogViewModel(
    private val getCatalogBooksUseCase: GetCatalogBooksUseCase
) : ViewModel() {
    var catalogBooks: LiveData<ProcessState<List<Book>?>> = MutableLiveData()

    fun loadResults(filter: String?, page: Int, size: Int) {
        catalogBooks =
            getCatalogBooksUseCase.invokeAsLiveData(GetCatalogBooksCaseIn(filter, page, size))
    }
}
