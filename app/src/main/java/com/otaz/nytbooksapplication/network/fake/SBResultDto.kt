package com.otaz.nytbooksapplication.network.fake

import com.google.gson.annotations.SerializedName
import com.otaz.nytbooksapplication.domain.model_fake.SBResult

data class SBResultDto(
    @SerializedName("author") var author: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("publisher") var publisher: String?,
    @SerializedName("title") var title: String
)

fun SBResultDto.toSBResult(): SBResult {
    return SBResult(
        author = author,
        description = description,
        publisher = publisher,
        title = title,
    )
}