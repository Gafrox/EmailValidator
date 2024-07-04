package com.example.emailvalidator.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY_TEST =
    "test_22f7db07e4a7eb5090c475f31ecb3b55ac5f1d1f7a39ba80f1225cf0eedb96c6"

private const val API_KEY_PROD =
    "live_88a902e569bc8c3dbd3ebf7f21e6935956c3fdf88d7034979713b4986b135222"

private const val BASE_URL = "https://api.kickbox.com/"

val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: RetrofitApi = retrofit.create(RetrofitApi::class.java)

interface RetrofitApi {

    @GET("v2/verify?apikey=$API_KEY_PROD")
    suspend fun checkEmail(@Query("email") email: String): Response
}