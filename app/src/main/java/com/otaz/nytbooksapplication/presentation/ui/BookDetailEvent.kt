package com.otaz.nytbooksapplication.presentation.ui

/**
 * This class is used to trigger events related to the MovieDetails
 */

sealed class BookDetailEvent {

    data class GetBookDetailEvent(
        val book_id: String,
    ): BookDetailEvent()
}