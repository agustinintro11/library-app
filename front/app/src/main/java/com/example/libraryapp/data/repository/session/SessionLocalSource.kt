package com.example.libraryapp.data.repository.session

import com.example.libraryapp.data.model.OrgApiKey
import com.example.libraryapp.data.model.Organization
import com.example.libraryapp.data.model.User
import com.example.libraryapp.data.repository.store.SharedPreferencesStore
import com.example.libraryapp.data.repository.store.converters.UserConverter

import com.google.gson.Gson

class SessionLocalSource(private val sharedPreferencesStore: SharedPreferencesStore, gson: Gson) {
    companion object {
        private const val SESSION_KEY_PREFIX = "session"
        private const val TOKEN_KEY = "$SESSION_KEY_PREFIX.tokenKey"
        private const val API_KEY = "$SESSION_KEY_PREFIX.apiKey"
        private const val USER_KEY = "$SESSION_KEY_PREFIX.userKey"
        private const val ORG_NAME = "$SESSION_KEY_PREFIX.orgNameKey"
    }

    private val userConverter = UserConverter(gson)

    suspend fun getSessionToken() = sharedPreferencesStore.getString(TOKEN_KEY)

    suspend fun getApiKey() = sharedPreferencesStore.getString(API_KEY)
    suspend fun setApiKey(apiKey: String) = sharedPreferencesStore.putString(API_KEY, apiKey)

    suspend fun getSessionOrg() = sharedPreferencesStore.getString(ORG_NAME)

    suspend fun getSessionUser() = sharedPreferencesStore.getEntity(USER_KEY, userConverter)

    suspend fun setSession(user: User, token: String, organization: Organization) {
        sharedPreferencesStore.putString(TOKEN_KEY, token)
        sharedPreferencesStore.putString(API_KEY, organization.apiKey)
        sharedPreferencesStore.putString(ORG_NAME, organization.name)
        sharedPreferencesStore.putEntity(USER_KEY, user, userConverter)
    }

    suspend fun destroySession() {
        sharedPreferencesStore.removeKey(TOKEN_KEY)
        sharedPreferencesStore.removeKey(API_KEY)
        sharedPreferencesStore.removeKey(USER_KEY)
    }
}
