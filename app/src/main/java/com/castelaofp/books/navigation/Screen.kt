package com.castelaofp.books.navigation

sealed class Screen(val route: String) {
    //Definimos primero las rutas de las pantallas
    object Login: Screen("login_screen")
    object Books: Screen("books_screen")
}