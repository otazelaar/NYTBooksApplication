package com.otaz.nytbooksapplication.network.fake

import com.google.gson.annotations.SerializedName

data class SBResponse(
    @SerializedName("results") var sbResultsDto: List<SBResultDto>,
)