package com.castelaofp.books.navigation // NavigationStack.kt

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.castelaofp.books.ui.screens.book.BookScreen
import com.castelaofp.books.ui.screens.book.BookViewModel
import com.castelaofp.books.ui.screens.login.LoginScreen
import com.castelaofp.books.ui.screens.login.LoginViewModel

/**
 * NavigationStack configura el menu de navegación para la aplicación.
 * Define las rutas de navegación y las pantallas asociadas.
 */
@Composable
fun NavigationStack() {
    // Crea un NavController para administrar la navegación de la app
    val navController = rememberNavController()
    var loginViewModel = LoginViewModel()
    var bookViewModel = BookViewModel()

    // TODO Configura NavHost para definir las rutas de navegación y la ruta de inicio
}