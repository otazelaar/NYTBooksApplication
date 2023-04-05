package com.otaz.nytbooksapplication.domain.use_case

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.data.db.BookDao
import com.otaz.nytbooksapplication.data.db.entitiesToBook
import com.otaz.nytbooksapplication.ui.event.BookListEvent.NewSearchDbEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * [SearchBookDbUC] retrieves a List<BookEntity> from the database most alike the user search input.
 * The List<BookEntity> is then mapped to List<Book> to update the state of the list of books to be
 * displayed in the UI.
 */

class SearchBookDbUC(
    private val bookDao: BookDao,
) {
    fun execute(
        search: String
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            // to display shimmer animation
            delay(700)

            val listOfBookEntity = bookDao.searchBooks(search)

            val listOfBook = entitiesToBook(listOfBookEntity)

            emit(DataState.success(listOfBook))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "SearchBookDbUC: Error"))
        }
    }
}