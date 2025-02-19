package com.castelaofp.books.network

import com.castelaofp.books.ui.screens.book.Book
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

private const val BASE_URL = "http://10.0.2.2:8080/api/biblioteca/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @GET("libros")
    suspend fun getBooks(): Response<List<Book>>

    @GET("libros/{libroId}")
    suspend fun getBookById(@Path("libroId") libroId: Int): Response<Book>

    @PUT("libros/{libroId}")
    suspend fun updateBook(@Path("libroId") libroId: Int, @Body book: Book): Response<Book>

    @DELETE("libros/{libroId}")
    suspend fun deleteBook(@Path("libroId") libroId: Int): Response<Void>

    @POST("libros/add")
    suspend fun createBook(@Body book: Book): Response<Book>

    @GET("libros/search")
    suspend fun searchBooks(@Query("q") q: String): Response<List<Book>>


}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}
