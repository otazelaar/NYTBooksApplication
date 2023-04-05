package com.otaz.nytbooksapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results") var results: Results,
)