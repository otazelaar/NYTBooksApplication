package com.otaz.nytbooksapplication.domain.model

import com.otaz.nytbooksapplication.data.db.BookEntity

/**
 * The [Book] data class models the data for a single book from the New York Times API.
 *      the applications business logic.
 *
 */

data class Book(
    val id: String,
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
fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        amazon_product_url = amazon_product_url,
        author = author,
        book_image = book_image,
        book_image_height = book_image_height,
        book_image_width = book_image_width,
        book_review_link = book_review_link,
        book_uri = book_uri,
        description = description,
        publisher = publisher,
        rank = rank,
        rank_last_week = rank_last_week,
        title = title,
        weeks_on_list = weeks_on_list
    )
}