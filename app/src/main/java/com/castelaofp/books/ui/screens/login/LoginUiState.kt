package com.castelaofp.books.ui.screens.login




/**
 * Created by Your name on 11/01/2024.
 */
data class LoginUiState (
    val loginEnable : Boolean = false,
    val isValidLogin : LoginValidEnum = LoginValidEnum.NA,
    val loginChecked: Boolean = false,
    val loginData: LoginData = LoginData("","")
)