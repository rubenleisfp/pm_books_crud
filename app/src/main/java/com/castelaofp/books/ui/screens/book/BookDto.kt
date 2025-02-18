package com.castelaofp.books.ui.screens.book

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Your name on 18/02/2025.
 */

@Serializable
data class BookDto (
    val id: Int,
    @SerialName(value = "titulo")
    val title: String,
    @SerialName(value = "autor")
    val author: String)
