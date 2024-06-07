package com.castelaofp.books.vm

/**
 * Created by Your name on 02/06/2024.
 */
data class Book (val id: Int,val title: String, val author: String)

/* Lista de libros que aparecen por defecto */
val books = listOf(
    Book(1, "La grieta del silencio","Javier Castillo"),
    Book(2, "Un animal salvaje","Joel Dicker"),
    Book(3, "Casa de tierra y sangre","Sarah J. Maas"),
    Book(4, "Hábitos atómicos","James Clear"),
)