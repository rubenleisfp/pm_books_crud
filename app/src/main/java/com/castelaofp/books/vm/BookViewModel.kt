package com.castelaofp.books.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookState())
    val uiState: StateFlow<BookState> = _uiState.asStateFlow()


    /**
     * Carga los libros de una lista
     * Para simular la carga de un repositorio: BBDD o API se realiza dentro de un scope con un delay de 3000
     *
     */
    fun loadDefault() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy (action = ActionEnum.IS_LOADING)
            delay(3000)
            _uiState.value = _uiState.value.copy(books = books, newBook = Book(1, "", ""), action = ActionEnum.CREATE)
        }
    }

    /**
     * Indica si los datos introducidos por el usuario tiene información o no
     */
    private fun hasInputData(newTitle: String, newAuthor: String): Boolean {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Agrega un nuevo libro a la lista existente de libros
     */
    fun addBook() {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Actualiza la información de un libro en la lista de libros
     */
    fun updateBook() {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Cuando pulsa en editar se rellenan las cajas de texto con el valor del libro escogido
     */
    fun editAction(book: Book) {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Actualizamos el titulo del libro introducido por teclado
     */
    fun setNewBookTitle(title: String) {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     *  Actualizamos el autor del libro introducido por teclado
     */
    fun setNewBookAuthor(author: String) {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Obtiene el siguiente id para asignarselo a un libro
     */
    private fun getNewId(): Int {
        throw UnsupportedOperationException("A completar por el alumno")
    }

    /**
     * Borra el libro recibido como argumento de la lista
     */
    fun removeBook(book: Book) {
        throw UnsupportedOperationException("A completar por el alumno")
    }
}