package com.castelaofp.books.vm

/**
 * Created by Your name on 02/06/2024.
 */
data class BookState(
    //Lista de libros que mostramos por la pantalla
    val books: List<Book> = emptyList(),
    // Libro que introduce el usuario por teclado para el create o update
    val isLoading: Boolean = true
)