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


    // Configura NavHost para definir las rutas de navegación y la ruta de inicio
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // Define una composable para la pantalla principal
        composable(route = Screen.Login.route) {
            // MainScreen es el punto de partida de la navegación
           LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        // Define una composable para la pantalla de de libros
        composable(
            route = Screen.Books.route
        ) {
            //Antes de iniciar la pantalla, cargamos los libros
            bookViewModel.loadDefault()
            BookScreen(navController = navController, bookViewModel = bookViewModel)
        }
    }
}