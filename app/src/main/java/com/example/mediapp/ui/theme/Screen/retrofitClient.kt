package com.example.mediapp.ui.theme.Screen

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"
    private const val USERNAME = "user"
    private const val PASSWORD = "bbcd7442-6cf3-47b1-ba3d-7bebf2b04df0"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun basicAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()

            if (originalRequest.header("Authorization") == null) {
                val credentials = Credentials.basic(USERNAME, PASSWORD)
                println("Generated Authorization: $credentials")  // <-- LOGUJEMY DANE
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", credentials)
                    .build()
                return@Interceptor chain.proceed(newRequest)
            }

            chain.proceed(originalRequest)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(basicAuthInterceptor())
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // ✅ Poprawione
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}