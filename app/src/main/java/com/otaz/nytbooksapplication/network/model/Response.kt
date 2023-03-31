package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results") var resultsDto: ResultsDto,
)
