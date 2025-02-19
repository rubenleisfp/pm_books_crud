package com.castelaofp.books.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Your name on 11/01/2024.
 */
@Serializable
data class LoginData (
       @SerialName(value = "email")
       var email: String="",
       @SerialName(value = "password")
       var password: String=""
)