package com.example.libraryapp.data.repository.store.converters

import androidx.annotation.WorkerThread
import com.example.libraryapp.data.model.User
import com.google.gson.Gson

class UserConverter(private val gson: Gson) : SharedPreferencesEntityConverter<User> {
    @WorkerThread
    override fun toString(t: User): String = gson.toJson(t)

    @WorkerThread
    override fun fromString(value: String?): User? = gson.fromJson(value, User::class.java)
}
