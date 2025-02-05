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
            _uiState.value = _uiState.value.copy(books = books, isLoading = false)
        }
    }

    /**
     * Borra el libro recibido como argumento de la lista
     */
    fun remove(book: Book) {
        throw UnsupportedOperationException("A implementar por el alumno")
    }
}