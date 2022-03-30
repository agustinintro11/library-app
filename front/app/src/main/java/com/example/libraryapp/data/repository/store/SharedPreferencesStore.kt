package com.example.libraryapp.data.repository.store

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.example.libraryapp.data.repository.store.converters.SharedPreferencesEntityConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SharedPreferencesStore {
    @WorkerThread
    fun getStringOnCurrentThread(key: String, defaultValue: String? = null): String?

    suspend fun getString(key: String, defaultValue: String? = null): String?
    fun putString(key: String, value: String?)

    suspend fun removeKey(key: String)

    suspend fun <T> getEntity(key: String, converter: SharedPreferencesEntityConverter<T>): T?
    suspend fun <T> putEntity(key: String, t: T, converter: SharedPreferencesEntityConverter<T>)
}

class SharedPreferencesStoreImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesStore {
    private val cachedEntity: MutableMap<String, Any?> = mutableMapOf()
    override suspend fun <T> putEntity(
        key: String,
        t: T,
        converter: SharedPreferencesEntityConverter<T>
    ) =
        withContext(Dispatchers.Default) {
            t.saveInCache(key)
            sharedPreferences.edit()
                .putString(key, converter.toString(t))
                .apply()
        }

    override fun putString(key: String, value: String?) =
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
            .saveInCache(key)

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getEntity(
        key: String,
        converter: SharedPreferencesEntityConverter<T>
    ): T? =
        (cachedEntity[key] as? T)
            ?: withContext(Dispatchers.Default) {
                val json = sharedPreferences.getString(key, null)
                converter.fromString(json)
            }.saveInCache(key)

    override fun getStringOnCurrentThread(key: String, defaultValue: String?): String? =
        (cachedEntity[key] as? String)
            ?: sharedPreferences.getString(key, defaultValue)
                .saveInCache(key)

    override suspend fun getString(key: String, defaultValue: String?): String? =
        (cachedEntity[key] as? String)
            ?: withContext(Dispatchers.Default) {
                sharedPreferences.getString(key, defaultValue)
                    .saveInCache(key)
            }

    private fun <T> T.saveInCache(key: String) = also { cachedEntity[key] = this }

    override suspend fun removeKey(key: String) {
        cachedEntity.remove(key)
        sharedPreferences.edit().remove(key).apply()
    }
}