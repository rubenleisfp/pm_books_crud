package com.castelaofp.books.network

/**
 * Created by Your name on 18/02/2025.
 *
 *
 */
import com.castelaofp.books.ui.screens.book.Book
import retrofit2.Retrofit

import retrofit2.http.GET
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


//Esta URL es la del anfitrion en el que se esta emulando la aplicación
private const val BASE_URL = "http://10.0.2.2:8080/api/biblioteca/"

private val json = Json {
    ignoreUnknownKeys = true // Ignorar campos desconocidos
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface BookApiService {
    @GET("libros")
    suspend fun getBooks(): List<Book>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}