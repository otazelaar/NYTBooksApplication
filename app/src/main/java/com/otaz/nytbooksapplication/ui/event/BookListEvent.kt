package com.otaz.nytbooksapplication.ui.event

/**
 * This class is used to trigger events related to the BookListViewModel
 */
sealed class BookListEvent {
    object NewCategorySearchEvent: BookListEvent()
    object NewSearchDbEvent: BookListEvent()
    object ResetForNextSearchEvent: BookListEvent()
}