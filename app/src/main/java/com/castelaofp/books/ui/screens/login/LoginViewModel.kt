package com.castelaofp.books.ui.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Your name on 11/01/2024.
 */

class LoginViewModel : ViewModel() {

    val TAG_LOG: String = "LoginViewModel"

    //Estado que representa la informacion que vemos en la pantalla
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el valor de loginData con los valores recibidos como argumento
     * Si son validos actualiza el valor de loginEnable en el UIState
     * Como el usuario esta haciendo cambios en su login, reseteamos la variable isValidLogin a NA
     */
    fun onLoginChanged(email: String, password: String) {
        //TODO:
        //1.-Crear un nuevo objeto LoginData que sustituira al actual de _uiState
        //2. Comprobar si los campos email y password son validos.
        // Si son campos validos, entonces habilitamos boton de login
        Log.d(TAG_LOG, "onLoginChanged")
        Log.d(TAG_LOG, "email: $email")
        Log.d(TAG_LOG, "password: $password")
        val newLoginData = LoginData(email, password)
        val areFieldsValid = isValidEmail(email) && isValidPassword(password)
        Log.d(TAG_LOG, "areFieldsValid $areFieldsValid")
        _uiState.update { currentState -> currentState.copy(loginEnable = areFieldsValid, loginData = newLoginData, isValidLogin = LoginValidEnum.NA   ) }
    }

    /**
     * Indica si es valor el password, longitud mayor de 3
     */
    private fun isValidPassword(password: String): Boolean = password.length > 2

    /**
     * Indica si es valido el email
     */
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * Se invoca cuando el usuario hace click en login
     * Comprueba que el login sea valido, es decir, que las credenciales sean Ok
     * Actualiza el UIState indicando si fue valido o no el login.
     */
    fun onLoginSelected(): LoginValidEnum {
        //1.- Comprobar si el login fue correcto o no y actualizar el UIState
        Log.d(TAG_LOG, "OnLoginSelected")

        val isValidLogin = isValidLogin()
        val validLoginEnum = if (isValidLogin) LoginValidEnum.OK else LoginValidEnum.KO
        //Se hará recompose por haber cambiado el uiState
        _uiState.update { currentState ->
            currentState.copy(isValidLogin = validLoginEnum)
        }
        return validLoginEnum
    }

    /**
     * Valida que el email sea a@b.com y el password 123
     */
    private fun isValidLogin(): Boolean {
        val isValidLogin = (_uiState.value.loginData.email == "a@b.com") && (_uiState.value.loginData.password == "123")
        return isValidLogin
    }
}

