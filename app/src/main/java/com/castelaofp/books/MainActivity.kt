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
import com.castelaofp.books.vm.ActionEnum
import com.castelaofp.books.vm.Book
import com.castelaofp.books.vm.BookState
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
 * @bookViewModel viewModel que contiene toda la logica del app
 */
@Composable
fun BookApp(
    bookViewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val bookState by bookViewModel.uiState.collectAsState()
    BookScreen(
        bookState = bookState,
        onNewBookTitleChange = { title -> bookViewModel.setNewBookTitle(title) },
        //Equivalente a esto cuando solo tiene un parametro la lambda
        //onNewBookTitleChange = { bookViewModel.setNewBookTitle(it) },
        onNewBookAuthorChange = { bookViewModel.setNewBookAuthor(it) },
        onAddBook = { bookViewModel.addBook() },
        onEditAction = { book -> bookViewModel.editAction(book) },
        onUpdateBook = { bookViewModel.updateBook() },
        onRemoveBook = { bookViewModel.removeBook(it) },
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
 *
 * @param bookState estado con la lista de libros y el nuevo libro
 * @param onNewBookTitleChange llamada cuando el usuario cambia el título del nuevo libro
 * @param onNewBookAuthorChange llamada cuando el usuario cambia el autor del nuevo libro
 * @param onAddBook llamada cuando el usuario pulsa el botón de agregar un nuevo libro
 * @param onUpdateBook llamada cuando el usuario pulsa el botón de actualizar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de borrar un libro
 * @param modifier modifier para el composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    bookState: BookState,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit,
    onAddBook: () -> Unit,
    onEditAction: (Book) -> Unit,
    onUpdateBook: () -> Unit,
    onRemoveBook: (Book) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    if (bookState.action == ActionEnum.IS_LOADING) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = modifier) {
            CamposTexto(bookState, onAddBook, onUpdateBook, onNewBookTitleChange, onNewBookAuthorChange)
            Spacer(modifier = Modifier.size(30.dp))
            BookList(
                books = bookState.books,
                onEditAction = { book -> onEditAction(book) },
                onRemoveBook = { book -> onRemoveBook(book) }
            )
        }
    }
}

/**
 * Contiene los campos de texto titulo y autor, que el usuario usará para
 * dar el alta/actualizar un libro
 *
 * @param bookState estado de la UI de mi app
 * @param onAddBook llamada cuando el usuario pulsa el botón de agregar un nuevo libro
 * @param onUpdateBook llamada cuando el usuario pulsa el botón de actualizar un libro
 * @param onNewBookTitleChange llamada cuando el usuario cambia el titulo del libro
 * @param onNewBookAuthorChange llamada cuando el usuario cambia el autor del libro
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CamposTexto(
    bookState: BookState,
    onAddBook: () -> Unit,
    onUpdateBook: () -> Unit,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit
) {
    Column() {
        Row {
            TextField(
                value = bookState.newBook.title,
                onValueChange = onNewBookTitleChange,
                singleLine = true,
                label = { Text(stringResource(R.string.input_titulo)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )

            TextField(
                value = bookState.newBook.author,
                onValueChange = onNewBookAuthorChange,
                singleLine = true,
                label = { Text(stringResource(R.string.input_author)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }
        if (bookState.action == ActionEnum.CREATE) {
            Button(
                onClick = { onAddBook() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(R.string.add_button))
            }
        }
        if (bookState.action == ActionEnum.MODIFY) {
            Button(
                onClick = { onUpdateBook() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(R.string.modify_button))
            }
        }
    }
}

/**
 * Mostramos la lista de libros
 *
 * @param books lista de libros a mostrar
 * @param onEditAction llamada cuando el usuario pulsa el botón de modificar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de borrar un libro
 * @param modifier modifier para el composable
 */
@Composable
fun BookList(
    books: List<Book>,
    onEditAction: (Book) -> Unit,
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
                onModify = {onEditAction(book)},
                onRemove = { onRemoveBook(book) }
            )
            Divider()
        }
    }
}

/**
 * Mostramos un elemento libro, con su titulo, autor y los iconos de accion
 *
 * @param book libro a mostrar
 * @param onModify llamada cuando el usuario pulsa el icono de modificar un libro
 * @param onRemove llamada cuando el usuario pulsa el icono de eliminar un libro
 * @param modifier modifier para el composable
 */
@Composable
fun BookItem(
    book: Book,
    onModify: () -> Unit,
    onRemove: () -> Unit,
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
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookScreenPreview() {
    val bookViewModel : BookViewModel = BookViewModel()
    val newBook = Book(id = 0, title = "", author = "")
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BookScreen(
            bookState = BookState(books = books, newBook = newBook, action = ActionEnum.CREATE),
            onNewBookTitleChange = { bookViewModel.setNewBookTitle(it) },
            onNewBookAuthorChange = { bookViewModel.setNewBookAuthor(it) },
            onAddBook = { bookViewModel.addBook() },
            onEditAction = { book -> bookViewModel.editAction(book) },
            onUpdateBook = { bookViewModel.updateBook() },
            onRemoveBook = { bookViewModel.removeBook(it) }
        )
    }
}