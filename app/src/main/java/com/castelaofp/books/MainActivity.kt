/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.castelaofp.books


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.castelaofp.books.ui.theme.BooksTheme
import com.castelaofp.books.vm.Book
import com.castelaofp.books.vm.BookViewModel
import com.castelaofp.books.vm.books


/**
 * Muestra una serie un catalogo de libros el cual podemos gestionar(CRUD)
 */
class MainActivity : ComponentActivity() {
    private val bookViewModel by viewModels<BookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookViewModel.loadDefault()

        setContent {
            BooksTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookApp(bookViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Obtiene informacion del viewModel: state y eventos
 *
 * Pasa esta informacion a BookScreen, la cual es la encarga de pintar
 * la pantalla y llamar a los eventos recibidos
 *
 * Obtenemos  lo que necesitamos del viewModel: uiState y eventos y los pasamos
 * al resto de métodos que los requieran
 *
 */
@Composable
fun BookApp(
    bookViewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val bookState by bookViewModel.uiState.collectAsState()
    BookScreen(
        isLoading = bookState.isLoading,
        books = bookState.books,
        newBook = bookState.newBook,
        onNewBookTitleChange = { title -> bookViewModel.setNewBookTitle(title) },
        onNewBookAuthorChange = { author -> bookViewModel.setNewBookAuthor(author) },
        onAddBook = { newTitle, newAuthor -> bookViewModel.add(newTitle, newAuthor) },
        onUpdateBook = { book, newTitle, newAuthor -> bookViewModel.updateText(book, newTitle, newAuthor) },
        onRemoveBook = { bookViewModel.remove(it) },
        modifier = modifier
    )
}

/**
 * Sirve para renderizar toda nuestra pantalla.
 * Durante la carga inicial se mostrara un CircleProgressBar, simulando la carga
 * de la info de un API
 * Desligada del viewModel para poder hacer previews de la pantalla.
 *
 * Mostramos:
 * -Las cajas de texto para crear/actualizar un nuevo libro
 * -El boton de añadir
 * -La lista con los libros incluidos hasta ahora
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    isLoading: Boolean,
    books: List<Book>,
    newBook: Book,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit,
    onAddBook: (String, String) -> Unit,
    onUpdateBook: (Book, String, String) -> Unit,
    onRemoveBook: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = modifier) {
            CamposTexto(newBook, onNewBookTitleChange, onNewBookAuthorChange)
            Button(
                onClick = { onAddBook(newBook.title, newBook.author) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(R.string.add_button))
            }
            Spacer(modifier = Modifier.size(30.dp))
            BookList(
                books = books,
                onModifyBook = { book -> onUpdateBook(book, newBook.title, newBook.author) },
                onRemoveBook = { book -> onRemoveBook(book) }
            )
        }
    }

}

/**
 * Contiene los campos de texto titulo y autor, que el usuario usará para
 * dar el alta/actualizar un libro
 *
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CamposTexto(
    newBook: Book,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit
) {
    Row {
        TextField(
            value = newBook.title,
            onValueChange = onNewBookTitleChange,
            singleLine = true,
            label = { Text(stringResource(R.string.input_titulo)) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )

        TextField(
            value = newBook.author,
            onValueChange = onNewBookAuthorChange,
            singleLine = true,
            label = { Text(stringResource(R.string.input_author)) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }
}

/**
 * Mostramos la lista de libros
 */
@Composable
fun BookList(
    books: List<Book>,
    onModifyBook: (Book) -> Unit,
    onRemoveBook: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = books,
            key = { book -> book.id }
        ) { book ->
            BookItem(
                book = book,
                onModify = {onModifyBook(book)},
                onClose = { onRemoveBook(book) }
            )
            Divider()
        }
    }
}

/**
 * Mostramos un elemento libro, con su titulo, autor y los iconos de accion
 */
@Composable
fun BookItem(
    book: Book,
    onModify: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = book.title,

            )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = book.author,

            )
        IconButton(onClick = onModify) {
            Icon(Icons.Filled.Edit, contentDescription = "Modify")
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookScreenPreview() {
    val newBook = Book(id = 0, title = "", author = "")
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BookScreen(
            isLoading=false,
            books = books,
            newBook = newBook,
            onNewBookTitleChange = {},
            onNewBookAuthorChange = {},
            onAddBook = {_,_->},
            onUpdateBook = { _, _, _ -> },
            onRemoveBook = {}
        )
    }
}