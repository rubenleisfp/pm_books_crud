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
import android.util.Log
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
import com.castelaofp.books.vm.BookViewModel
import com.castelaofp.books.vm.books


/**
 * Muestra una serie un catalogo de libros el cual podemos gestionar(CRUD)
 */
class MainActivity : ComponentActivity() {

    //TODO
    //Hay que crear una instancia del viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO
        //Invocar el metodo de carga de libros del viewModel
        setContent {
            BooksTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookApp()
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
 */
@Composable
fun BookApp(
    modifier: Modifier = Modifier
) {
    //TODO
    //Recibiremos el viewModel. De El podemos extraer el bookState, que contendra
    //los libros. No podemos cargarlos directamente de la clase Book. Pasaremos el evento remove a
    //a BookScreen. Tambien pasaremos el isLoading para mostrar la animacion mientras cargan
    BookScreen(
        books = books,
        modifier = modifier
    )
}

/**
 * Sirve para renderizar toda nuestra pantalla.
 * Durante la carga inicial se mostrara un CircleProgressBar, simulando la carga de la info de un API
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
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    //TODO: si estan cargando debemos mostrar un CircularProgressIndicator
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
    //TODO: pasaremos el evento onRemove a BookList
    Column(modifier = modifier) {
        //TODO: pasaremos el evento onRemove a BookList
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = books,
                key = { book -> book.id }
            ) { book ->
                BookItem(
                    book = book

                )
                Divider()
            }
        }
    }
}


/**
 * Mostramos un elemento libro, con su titulo, autor y los iconos de accion
 */
@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    //TODO: invocaremos el evento onRemove al hacer click
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
        IconButton(onClick = { Log.i("MainActivity", "onDeleteClick") }) {
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
            books = books,
        )
    }
}