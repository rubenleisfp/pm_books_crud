package com.castelaofp.books.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * TODO Se proporcionan las firmas de los métodos, por si te sirven de ayuda para completarlas.
 * Tambien puedes borrarlas y hacerlas a tu gusto
 *
 */
class BookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookState())
    val uiState: StateFlow<BookState> = _uiState.asStateFlow()


    /**
     * Carga los libros de una lista
     * Para simular la carga de un repositorio: BBDD o API se realiza dentro de un scope con un delay de 3000
     *
     */
    fun loadDefault() {

    }

    /**
     * Indica si los datos introducidos por el usuario tiene información o no
     */
    private fun hasInputData(newTitle: String, newAuthor: String): Boolean {
        return true
    }

    /**
     * Agrega un nuevo libro a la lista existente de libros
     */
    fun add(newTitle: String, newAuthor: String) {

    }

    /**
     * Actualiza la información de un libro en la lista de libros
     * Recibe un libro y la nueva información que queremos establecer en dicho libro: titulo y autor
     */
    fun updateText(book: Book, newTitle: String, newAuthor: String) {

    }

    /**
     * Actualizamos el titulo del libro introducido por teclado
     */
    fun setNewBookTitle(title: String) {

    }

    /**
     *  Actualizamos el autor del libro introducido por teclado
     */
    fun setNewBookAuthor(author: String) {

    }

    /**
     * Obtiene el siguiente id para asignarselo a un libro
     */
    fun getNewId(): Int {
        return 0
    }

    /**
     * Borra el libro recibido como argumento de la lista
     */
    fun remove(book: Book) {

    }
}