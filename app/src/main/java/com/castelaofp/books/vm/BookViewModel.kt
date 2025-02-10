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
            _uiState.value = _uiState.value.copy (isLoading=true)
            delay(3000)
            _uiState.value = _uiState.value.copy(books = books, newBook = Book(1, "", ""), isLoading = false)
        }
    }

    /**
     * Indica si los datos introducidos por el usuario tiene información o no
     */
    private fun hasInputData(newTitle: String, newAuthor: String): Boolean {
        return (newTitle.isNotEmpty() && newAuthor.isNotEmpty())
    }

    /**
     * Agrega un nuevo libro a la lista existente de libros
     */
    fun add() {
        if (hasInputData(_uiState.value.newBook.title, _uiState.value.newBook.author)) {
            val newBook = Book(getNewId(), _uiState.value.newBook.title, _uiState.value.newBook.author)
            val updatedBooks = _uiState.value.books + newBook
            _uiState.value = _uiState.value.copy(books = updatedBooks, newBook = Book(getNewId(), "", ""))
        }
    }

    /**
     * Actualiza la información de un libro en la lista de libros
     *
     * @param book libro que deseamos modificar
     */
    fun updateBook(book: Book) {
        if (hasInputData(_uiState.value.newBook.title, _uiState.value.newBook.author)) {
            val updatedBooks = _uiState.value.books.map {
                if (it.id == book.id) it.copy(title = _uiState.value.newBook.title, author = _uiState.value.newBook.author)
                else it
            }
            _uiState.value = _uiState.value.copy(books = updatedBooks)
        }
    }

    /**
     * Actualizamos el titulo del libro introducido por teclado
     */
    fun setNewBookTitle(title: String) {
        _uiState.value = _uiState.value.copy(newBook = _uiState.value.newBook.copy(title = title))
    }

    /**
     *  Actualizamos el autor del libro introducido por teclado
     */
    fun setNewBookAuthor(author: String) {
        _uiState.value = _uiState.value.copy(newBook = _uiState.value.newBook.copy(author = author))
    }

    /**
     * Obtiene el siguiente id para asignarselo a un libro
     */
    fun getNewId(): Int {
        val lastId = _uiState.value.books.lastOrNull()?.id ?: 0
        return lastId + 1
    }

    /**
     * Borra el libro recibido como argumento de la lista
     */
    fun remove(book: Book) {
        val updatedBooks = _uiState.value.books.filterNot { it == book }
        _uiState.value = _uiState.value.copy(books = updatedBooks)
    }
}