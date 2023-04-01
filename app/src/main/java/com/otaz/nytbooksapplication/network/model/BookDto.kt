package com.otaz.nytbooksapplication.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.model.toBookEntity
import com.otaz.nytbooksapplication.persistance.BookEntity

data class BookDto(
    @SerializedName("primary_isbn13") var primary_isbn13: String,
    @SerializedName("amazon_product_url") var amazon_product_url: String,
    @SerializedName("author") var author: String,
    @SerializedName("book_image") var book_image: String,
    @SerializedName("book_image_height") var book_image_height: Int,
    @SerializedName("book_image_width") var book_image_width: Int,
    @SerializedName("book_review_link") var book_review_link: String,
    @SerializedName("book_uri") var book_uri: String,
    @SerializedName("description") var description: String,
    @SerializedName("publisher") var publisher: String,
    @SerializedName("rank") var rank: Int,
    @SerializedName("rank_last_week") var rank_last_week: Int,
    @SerializedName("title") var title: String,
    @SerializedName("weeks_on_list") var weeks_on_list: Int
)

fun BookDto.toBookEntity(): BookEntity {
    return BookEntity(
        primary_isbn13 = primary_isbn13,
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

fun BookDto.toBook(): Book {
    return Book(
        primary_isbn13 = primary_isbn13,
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

fun bookToEntity(books: List<Book>): List<BookEntity> {
    return books.map { it.toBookEntity() }
}