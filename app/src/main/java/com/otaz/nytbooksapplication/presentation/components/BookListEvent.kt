package com.otaz.nytbooksapplication.presentation.components

sealed class BookListEvent {
    object NewSearchEvent: BookListEvent()
}