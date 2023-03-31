package com.otaz.nytbooksapplication.domain.model

data class Book(
    val amazon_product_url: String,
    val author: String,
    val book_image: String,
    val book_image_height: Int,
    val book_image_width: Int,
    val book_review_link: String,
    val book_uri: String,
    val description: String,
    val publisher: String,
    val rank: Int,
    val rank_last_week: Int,
    val title: String,
    val weeks_on_list: Int
)