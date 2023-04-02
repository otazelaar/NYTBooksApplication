package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.nytbooksapplication.domain.model.Article

data class ArticleDto(

    @SerializedName("abstract")
    var abstract: String,

    @SerializedName("byline")
    var adx_keywords: String,

    @SerializedName("byline")
    var author: String,

    @SerializedName("des_facet")
    var des_facet: List<String>,

    @SerializedName("id")
    var id: Long,

    @SerializedName("media")
    var media: List<Media>,

    @SerializedName("published_date")
    var published_date: String,

    @SerializedName("section")
    var section: String,

    @SerializedName("source")
    var source: String,

    @SerializedName("subsection")
    var subsection: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("url")
    var url: String
)

fun ArticleDto.mapToArticle(): Article{
    return Article(
        abstract = abstract,
        adx_keywords = adx_keywords,
        author = author,
        des_facet = des_facet,
        id = id,
        media = media,
        published_date = published_date,
        section = section,
        source = source,
        subsection = subsection,
        title = title,
        type = type,
        url = url,
    )
}