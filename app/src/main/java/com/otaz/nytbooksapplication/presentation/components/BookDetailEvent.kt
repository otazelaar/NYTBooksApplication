package com.otaz.nytbooksapplication.presentation.components

/**
 * This class is used to trigger events related to the MovieDetails
 */

sealed class BookDetailEvent {

    data class GetBookDetailEvent(
        val title: String,
    ): BookDetailEvent()
}