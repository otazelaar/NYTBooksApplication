package com.otaz.nytbooksapplication.domain.model_fake

import com.otaz.nytbooksapplication.persistance.SBResultEntity

data class SBResult(
    val author: String?,
    val description: String?,
    val publisher: String?,
    val title: String
)

fun SBResult.toResultEntity(): SBResultEntity {
    return SBResultEntity(
        author = author,
        description = description,
        publisher = publisher,
        title = title,
    )
}