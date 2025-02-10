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
            _uiState.value = _uiState.value.copy(books = books, newBook = Book(1, "", ""), action = ActionEnum.READ)
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
            val newBook = Book(getNewId(),_uiState.value.newBook.title, _uiState.value.newBook.author)
            val updatedBooks = _uiState.value.books + newBook
            _uiState.value = _uiState.value.copy(action = ActionEnum.READ, books = updatedBooks, newBook = Book(getNewId(), "", ""))
        }
    }


    /**
     * Actualiza la información de un libro en la lista de libros
     */
    fun updateBook() {
        if (hasInputData(_uiState.value.newBook.title, _uiState.value.newBook.author)) {
            val updatedBooks = mutableListOf<Book>()
            for (book in _uiState.value.books) {
                if (book.id == _uiState.value.newBook.id) {
                    updatedBooks.add(book.copy(title = _uiState.value.newBook.title, author = _uiState.value.newBook.author))
                } else {
                    updatedBooks.add(book)
                }
            }
            _uiState.value = _uiState.value.copy(books = updatedBooks)
            _uiState.value = _uiState.value.copy(action = ActionEnum.READ)
        }
    }



    /**
     * Prepara la creación de un nuevo libro.
     *
     * Al invocar esta función, se cambia el estado a CREATE para que el usuario pueda crear un nuevo libro.
     * Se crea un nuevo libro con un id nuevo y se inicializan sus campos con cadenas vacías.
     */
    fun nuevoAction() {
        _uiState.value = _uiState.value.copy(action = ActionEnum.CREATE, newBook = Book(getNewId(), "", ""))
    }

    /**
     * Cancela la creación o edición de un libro.
     *
     * Al recibir esta función, se cambia el estado a READ para que el usuario pueda ver la lista de libros
     * y se borra el libro que se estaba creando o editando.
     */
    fun cancelAction() {
        _uiState.value = _uiState.value.copy(action = ActionEnum.READ)
    }

    /**
     * Prepara la edición de un libro.
     *
     * Al recibir como parámetro el libro a editar, se copian sus datos en el nuevo libro
     * y se cambia el estado a MODIFY para que el usuario pueda editar el libro.
     *
     * @param book libro que se va a editar
     */
    fun editAction(book: Book) {
        _uiState.value = _uiState.value.copy(action = ActionEnum.MODIFY)
        _uiState.value = _uiState.value.copy(newBook = book)
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