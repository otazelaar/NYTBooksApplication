package com.otaz.nytbooksapplication.domain.model

import com.otaz.nytbooksapplication.network.model.Media

data class Article(
    val abstract: String,
    val adx_keywords: String,
    val author: String,
    val des_facet: List<String>,
    val id: Long,
    val media: List<Media>,
    val published_date: String,
    val section: String,
    val source: String,
    val subsection: String,
    val title: String,
    val type: String,
    val url: String
)