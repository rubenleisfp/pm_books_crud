package com.castelaofp.books.ui.screens.book

/**
 * Created by Your name on 02/06/2024.
 */
data class BookState(
    //Lista de libros que mostramos por la pantalla
    val books: List<Book> = emptyList(),
    // Libro que introduce el usuario por teclado para el create o update
    val newBook: Book = Book(0, "", ""),
    val action: ActionEnum = ActionEnum.READ,
    val searchWord: String = ""

)