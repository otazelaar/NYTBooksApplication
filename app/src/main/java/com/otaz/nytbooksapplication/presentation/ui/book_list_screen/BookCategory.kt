package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookCategory.*

enum class BookCategory(val value: String){
    GET_HARDCOVER_FICTION("Hardcover Fiction"),
    GET_HARDCOVER_NONFICTION("Hardcover Nonfiction"),
}

fun getAllBookCategories(): List<BookCategory>{
    return listOf(GET_HARDCOVER_FICTION, GET_HARDCOVER_NONFICTION)
}

fun getBookCategory(value: String): BookCategory? {
    val map = values().associateBy(BookCategory::value)
    return map[value]
}