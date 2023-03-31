package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName

data class ResultsDto(
    @SerializedName("bestsellers_date") var bestsellers_date: String,
    @SerializedName("books") var books: List<BookDto>,
    @SerializedName("display_name") var display_name: String,
    @SerializedName("list_name") var list_name: String,
    @SerializedName("list_name_encoded") var list_name_encoded: String,
    @SerializedName("next_published_date") var next_published_date: String,
    @SerializedName("normal_list_ends_at") var normal_list_ends_at: Int,
    @SerializedName("previous_published_date") var previous_published_date: String,
    @SerializedName("published_date") var published_date: String,
    @SerializedName("published_date_description") var published_date_description: String,
    @SerializedName("updated") var updated: String
)