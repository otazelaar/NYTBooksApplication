package com.otaz.nytbooksapplication.presentation.ui

sealed class BookListEvent {
    object NewCategorySearchEvent: BookListEvent()
    object NewSearchDbEvent: BookListEvent()
    object ResetForNextSearchEvent: BookListEvent()
}