package com.castelaofp.books.network

/**
 * Created by Your name on 18/02/2025.
 *
 *
 */
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

//Esta URL es la del anfitrion en el que se esta emulando la aplicación
private const val BASE_URL = "http://10.0.2.2:8080/api/biblioteca/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @GET("libros")
    suspend fun getBooks(): String
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}