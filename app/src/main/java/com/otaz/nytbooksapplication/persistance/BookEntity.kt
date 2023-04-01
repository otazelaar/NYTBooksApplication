package com.otaz.nytbooksapplication.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.network.model.BookDto

@Entity(tableName = "books")
data class BookEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val primary_isbn13: String,

    @ColumnInfo(name = "amazonProductUrl")
    val amazon_product_url: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "bookImage")
    val book_image: String,

    @ColumnInfo(name = "bookImageHeight")
    val book_image_height: Int,

    @ColumnInfo(name = "bookImageWidth")
    val book_image_width: Int,

    @ColumnInfo(name = "bookReviewLink")
    val book_review_link: String,

    @ColumnInfo(name = "bookUri")
    val book_uri: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "publisher")
    val publisher: String,

    @ColumnInfo(name = "rank")
    val rank: Int,

    @ColumnInfo(name = "rankLastWeek")
    val rank_last_week: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "weeksOnList")
    val weeks_on_list: Int
)

fun BookEntity.toBook(): Book {
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