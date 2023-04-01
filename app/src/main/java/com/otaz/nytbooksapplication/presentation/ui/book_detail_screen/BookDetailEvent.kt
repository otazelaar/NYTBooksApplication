package com.otaz.nytbooksapplication.presentation.ui.book_detail_screen

/**
 * This class is used to trigger events related to the BookDetails
 */

sealed class BookDetailEvent {

    data class GetBookDetailEvent(
        val book_id: String,
    ): BookDetailEvent()
}