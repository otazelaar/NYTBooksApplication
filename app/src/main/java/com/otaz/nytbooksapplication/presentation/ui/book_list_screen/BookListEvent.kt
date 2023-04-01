package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

sealed class BookListEvent {
    object NewSearchEvent: BookListEvent()
}