package com.example.libraryapp.ui.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.example.libraryapp.device.ProcessState

inline fun <T> LiveData<ProcessState<T>>.observeStateResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit = {},
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {}
) = observe(viewLifecycleOwner) { processState ->
    when (processState) {
        is ProcessState.Loading -> onLoading.invoke()
        else -> invokeResultCallback(
            (processState as ProcessState.Finish).result,
            onSuccess,
            onFailure
        )
    }
}

inline fun <T> invokeResultCallback(
    result: com.example.libraryapp.domain.Result<T>,
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit
) = when (result) {
    is com.example.libraryapp.domain.Result.Success -> onSuccess.invoke(result.value)
    is com.example.libraryapp.domain.Result.Error -> onFailure.invoke(result.throwable!!)
    else -> onFailure.invoke(NotImplementedError())
}
