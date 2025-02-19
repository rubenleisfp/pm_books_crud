package com.castelaofp.books.ui.screens.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.castelaofp.books.network.BookApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookState())
    val uiState: StateFlow<BookState> = _uiState.asStateFlow()

    private val LOG_TAG = "BookViewModel"

    /**
     * Carga los libros de un API
     *
     * En caso de exito se actualiza el estado con la nueva lista de libros y se vuelve a mostrar
     * En caso de error se muestra el error y se vuelve a mostrar el estado de error
     *
     */
    fun loadBooks() {
        Log.i(LOG_TAG, "Loading books")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy (action = ActionEnum.IS_LOADING)
            try {
                val response = BookApi.retrofitService.getBooks()
                if (response.isSuccessful) {
                    val bookList = response.body() ?: emptyList()
                    _uiState.value = _uiState.value.copy(bookList, newBook = Book(1, "", ""), action = ActionEnum.READ)
                    Log.i(LOG_TAG, "Load was Ok")
                } else {
                    _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                    Log.e(LOG_TAG, "Load was NOT Ok")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                Log.e(LOG_TAG, "Exception: $e")
            }

        }
    }

    /**
     * Indica si los datos introducidos por el usuario tiene información o no
     */
    private fun hasInputData(newTitle: String, newAuthor: String): Boolean {
        return (newTitle.isNotEmpty() && newAuthor.isNotEmpty())
    }

    /**
     * Agrega un nuevo libro a la lista existente de libros llamando al API
     */
    fun addBook() {
        if (hasInputData(_uiState.value.newBook.title, _uiState.value.newBook.author)) {
            viewModelScope.launch {
                try {
                    val newBook = Book(getNewId(), _uiState.value.newBook.title, _uiState.value.newBook.author)
                    val response = BookApi.retrofitService.createBook(newBook)
                    if (response.isSuccessful) {
                        Log.i(LOG_TAG, "Add Book was Ok")
                        loadBooks() // refresh the list of books
                    } else {
                        _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                        Log.e(LOG_TAG, "Add Book failed")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                    Log.e(LOG_TAG, "Exception: $e")
                }
                loadBooks()
            }
        }
    }


    /**
     * Actualiza la información de un libro en la lista de libros
     */
    fun updateBook() {
        Log.i(LOG_TAG, "Updating Book with this info: ${_uiState.value.newBook}")
        if (hasInputData(_uiState.value.newBook.title, _uiState.value.newBook.author)) {
            viewModelScope.launch {
                try {
                    val response = BookApi.retrofitService.updateBook(
                        _uiState.value.newBook.id,
                        _uiState.value.newBook
                    )
                    if (response.isSuccessful) {
                        Log.i(LOG_TAG, "Update was Ok")
                    } else {
                        _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                        Log.e(LOG_TAG, "Update was NOT Ok")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                    Log.e(LOG_TAG, "Exception: $e")
                }
                //Despues de actualizar cargamos de nuevo los books
                loadBooks()
            }
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
     * Elimina un libro de la lista de libros llamando al API
     */
    fun removeBook(book: Book) {
        Log.i(LOG_TAG, "Removing Book with this info: ${book.id}")
        viewModelScope.launch {
            try {
                val response = BookApi.retrofitService.deleteBook(book.id)
                if (response.isSuccessful) {
                    Log.i(LOG_TAG, "Remove was Ok")
                } else {
                    _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                    Log.e(LOG_TAG, "Remove was NOT Ok")
                }
                Log.i("BookViewModel", "Remove was Ok")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                Log.e("BookViewModel", "Exception: $e")
            }
            loadBooks()
        }
    }



    /**
     * Realiza una búsqueda en la lista de libros por el título o autor.
     * Si encuentra resultados, actualiza el estado con la nueva lista de libros.
     * Si no encuentra, actualiza el estado con el error para que se muestre
     * en la pantalla
     *
     * @param searchWord palabra o frase a buscar
     */
    fun searchAction(searchWord: String) {
        Log.i(LOG_TAG, "Searching word: ${searchWord}")
        _uiState.value = _uiState.value.copy(searchWord = searchWord)
        viewModelScope.launch {
            try {
                val response = BookApi.retrofitService.searchBooks(searchWord)
                if (response.isSuccessful) {
                    Log.i(LOG_TAG, "Search was Ok")
                    val bookList = response.body() ?: emptyList()
                    _uiState.value = _uiState.value.copy(books = bookList, searchWord = searchWord)
                } else {
                    Log.i(LOG_TAG, "Search was Ok")
                    _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(action = ActionEnum.ERROR)
                Log.e(LOG_TAG, "Exception: $e")
            }
        }

    }


}