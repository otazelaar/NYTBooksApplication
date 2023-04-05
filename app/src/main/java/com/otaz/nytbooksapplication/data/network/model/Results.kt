package com.otaz.nytbooksapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("books") var booksDto: List<BookDto>,
)