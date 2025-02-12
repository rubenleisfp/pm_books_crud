package com.castelaofp.books.navigation // NavigationStack.kt

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.castelaofp.books.ui.theme.screens.login.LoginScreen

/**
 * NavigationStack configura el grafo de navegación para la aplicación.
 * Define las rutas de navegación y las pantallas asociadas.
 */
@Composable
fun NavigationStack() {
    // Crea un NavController para administrar la navegación de la app
    val navController = rememberNavController()

    // Configura NavHost para definir las rutas de navegación y la ruta de inicio
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // Define una composable para la pantalla principal
        composable(route = Screen.Login.route) {
            // MainScreen es el punto de partida de la navegación
            //LoginScreen(navController = navController)
        }
        // Define una composable para la pantalla de detalles con un argumento opcional "text"
        composable(
            route = Screen.Books.route+ "?text={text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = true // Permite que el argumento "text" sea opcional
                }
            )
        ) {
            // Pasa el argumento "text" a la pantalla de detalles
            //DetailScreen(navController = navController, text = it.arguments?.getString("text"))
        }
    }
}