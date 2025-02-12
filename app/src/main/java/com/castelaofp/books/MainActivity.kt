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
import com.castelaofp.books.navigation.NavigationStack
import com.castelaofp.books.ui.theme.BooksTheme
import com.castelaofp.books.ui.screens.book.BookViewModel
import com.castelaofp.books.ui.screens.login.LoginViewModel


/**
 * Muestra una serie un catalogo de libros el cual podemos gestionar(CRUD)
 */
class MainActivity : ComponentActivity() {
    private val bookViewModel by viewModels<BookViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookViewModel.loadDefault()

        setContent {
            BooksTheme() {
                /**
                 * NavigationStack es una funcion que define la navegacion entre pantallas.
                 * La navegacion se define como una serie de pantallas que se pueden navegar
                 * con el boton de navegacion.
                 */
                //TODO LLamar a NavigationStack
            }
        }
    }
}

