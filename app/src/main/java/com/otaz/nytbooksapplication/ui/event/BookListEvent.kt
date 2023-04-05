package com.otaz.nytbooksapplication.ui.event

sealed class BookListEvent {
    object NewCategorySearchEvent: BookListEvent()
    object NewSearchDbEvent: BookListEvent()
    object ResetForNextSearchEvent: BookListEvent()
}