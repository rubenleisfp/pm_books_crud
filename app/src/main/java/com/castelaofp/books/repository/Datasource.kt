package com.castelaofp.books.repository

import com.castelaofp.books.vm.Book

/**
 * Created by Your name on 11/02/2025.
 */
class Datasource {

   fun getBooks(): List<Book> {
       /* Lista de libros que aparecen por defecto */
       val books = listOf(
           Book(1, "La grieta del silencio","Javier Castillo"),
           Book(2, "Un animal salvaje","Joel Dicker"),
           Book(3, "Casa de tierra y sangre","Sarah J. Maas"),
           Book(4, "Hábitos atómicos","James Clear"),
       )
       return books
    }
}