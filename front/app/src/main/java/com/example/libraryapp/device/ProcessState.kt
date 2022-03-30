package com.example.libraryapp.device

import com.example.libraryapp.domain.Result

sealed class ProcessState<out R> {
    data class Finish<out T>(val result: Result<T>) : ProcessState<T>()
    object Loading : ProcessState<Nothing>()

    override fun toString(): String = when (this) {
        is Loading -> "Loading"
        is Finish<*> -> "Finish = Result[$result]"
    }
}