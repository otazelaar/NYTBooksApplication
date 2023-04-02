package com.otaz.nytbooksapplication.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaz.nytbooksapplication.domain.model_fake.SBResult

@Entity(tableName = "books")
data class SBResultEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "publisher")
    val publisher: String?,
)

fun SBResultEntity.toSBResult(): SBResult {
    return SBResult(
        title = title,
        author = author,
        description = description,
        publisher = publisher,
    )
}