package com.castelaofp.books.ui.screens.book

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Your name on 02/06/2024.
 */
@Serializable
data class Book (
    val id: Int,
    @SerialName(value = "titulo")
    val title: String,
    @SerialName(value = "autor")
    val author: String)

