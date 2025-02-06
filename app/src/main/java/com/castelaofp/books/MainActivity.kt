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
import androidx.lifecycle.viewmodel.compose.viewModel
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
 * @param bookViewModel El viewModel asociado a la pantalla
 * @param modifier Un modificador opcional para ajustar la apariencia y el diseño del componente.
 */
@Composable
fun BookApp(
    bookViewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    /**
     * No es conveniente pasar el viewModel a otras funciones, para evitar
     * estar acoplados a este elemento.
     * Por eso en este punto obtenemos lo que necesitamos
     * tanto del UIState (información a pintar en la pantalla) como del ViewModel (eventos que
     * queremos llamar desde la UI). Ver más en teoría, apartado viewModel - Buenas Prácticas
     *
     */
    val bookState by bookViewModel.uiState.collectAsState()
    BookScreen(
        bookState = bookState,
        //Paso una funcion que lambda. Recibe un libro y en el cuerpo de la funcion se llama al
        //viewModel para eliminarlo
        onRemoveBook = {book -> bookViewModel.remove(book) },
        modifier = modifier
    )
    //Otra manera mas abreviada de hacer la lambda. Cuando solo hay un argumento se puede nombrar como it
    //onRemoveBook = {bookViewModel.remove(it) },
}

/**
 * Sirve para renderizar toda nuestra pantalla.
 * Durante la carga inicial se mostrara un CircleProgressBar, simulando la carga de la info de un API
 * Desligada del viewModel para poder hacer previews de la pantalla.
 *
 * @param bookState El estado actual de los libros, que contiene la lista de libros y el estado de carga.
 * @param onRemoveBook Una función lambda que recibe un libro y realiza la acción de eliminarlo.
 * @param modifier Un modificador opcional para ajustar la apariencia y el diseño del componente.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    bookState: BookState,
    onRemoveBook: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    if (bookState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = bookState.books,
                key = { book -> book.id }
            ) { book ->
                BookItem(
                    book = book,
                    onRemoveBook = { onRemoveBook(book) }
                )
                Divider()
            }
        }
    }
}

/**
 * Sirve para renderizar toda nuestra pantalla.
 * Durante la carga inicial se mostrara un CircleProgressBar, simulando la carga de la info de un API.
 * Desligada del viewModel para poder hacer previews de la pantalla.
 *
 * Mostramos:
 * - Las cajas de texto para crear/actualizar un nuevo libro.
 * - El botón de añadir.
 * - La lista con los libros incluidos hasta ahora.
 *
 * @param bookState El estado actual de los libros, que contiene la lista de libros y el estado de carga.
 * @param onRemoveBook Una función lambda que recibe un libro y realiza la acción de eliminarlo.
 * @param modifier Un modificador opcional para ajustar la apariencia y el diseño del componente.
 */
@Composable
fun BookItem(
    book: Book,
    onRemoveBook: () -> Unit,
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
        IconButton(onClick = onRemoveBook) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookScreenPreview() {
    val bookState = BookState(emptyList(), isLoading = false)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BookScreen(
            bookState = bookState,
            onRemoveBook = {}
        )
    }
}