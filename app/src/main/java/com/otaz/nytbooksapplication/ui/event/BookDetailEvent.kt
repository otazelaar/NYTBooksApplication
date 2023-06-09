package com.otaz.nytbooksapplication.ui.event

/**
 * This class is used to trigger events related to the BookDetailViewModel
 */
sealed class BookDetailEvent {

    data class GetBookDetailEvent(
        val book_id: String,
    ): BookDetailEvent()
}