package com.otaz.nytbooksapplication.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.nytbooksapplication.network.model.BookDto

/**
 * This response contains a list of books that
 */

data class BookResponse(
    @SerializedName("results") var books: List<BookDto>,
)