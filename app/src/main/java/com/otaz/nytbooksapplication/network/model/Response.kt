package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("num_results")
    val num_results: Int,

    @SerializedName("results")
    val articleDtos: List<ArticleDto>,
)