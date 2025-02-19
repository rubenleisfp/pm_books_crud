package com.castelaofp.books.network

import com.castelaofp.books.ui.screens.book.Book
import com.castelaofp.books.ui.screens.login.LoginData
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Query
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "http://10.0.2.2:8080/api/admin/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AdminApiService {
    @POST("login")
    suspend fun login(@Body loginData: LoginData): Response<Void>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AdminApi {
    val retrofitService: AdminApiService by lazy {
        retrofit.create(AdminApiService::class.java)
    }
}
