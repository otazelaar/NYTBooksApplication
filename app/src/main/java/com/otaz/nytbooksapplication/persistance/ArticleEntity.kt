package com.otaz.nytbooksapplication.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaz.nytbooksapplication.domain.model.Article
import com.otaz.nytbooksapplication.network.model.Media

@Entity(tableName = "articles")
data class ArticleEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "abstract")
    var abstract: String,

    @ColumnInfo(name = "adx_keywords")
    var adx_keywords: String,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "desFacet")
    var des_facet: List<String>,

    @ColumnInfo(name = "media")
    var media: List<Media>,

    @ColumnInfo(name = "publishedDate")
    var published_date: String,

    @ColumnInfo(name = "section")
    var section: String,

    @ColumnInfo(name = "source")
    var source: String,

    @ColumnInfo(name = "subsection")
    var subsection: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "url")
    var url: String

)

fun ArticleEntity.toArticle(): Article {
    return Article(
        abstract = abstract,
        adx_keywords = adx_keywords,
        author = author,
        des_facet = des_facet,
        id = id,
        media = media,
        published_date = published_date,
        section =section,
        source = source,
        subsection = subsection,
        title = title,
        type = type,
        url = url
    )
}
