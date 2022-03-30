package com.example.libraryapp.data.repository.store.converters

import androidx.annotation.WorkerThread

interface SharedPreferencesEntityConverter<T> {
    @WorkerThread
    fun toString(t: T): String

    @WorkerThread
    fun fromString(value: String?): T?
}