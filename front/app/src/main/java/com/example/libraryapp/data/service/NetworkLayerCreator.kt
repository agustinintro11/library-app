package com.example.libraryapp.data.service

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object NetworkLayerCreator {
    private const val API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun createRetrofitInstance(
        baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient().build())
        .addConverterFactory(createGsonConverterFactory())
        .build()

    private fun createGsonConverterFactory() = GsonBuilder()
        .setDateFormat(API_DATE_FORMAT)
        .create()
        .let { GsonConverterFactory.create(it) }

    private fun httpClient(): OkHttpClient.Builder{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging)
    }
}
