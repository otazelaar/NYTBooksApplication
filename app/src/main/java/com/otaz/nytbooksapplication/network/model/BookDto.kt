package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("id") var id: Int,
)