package com.otaz.nytbooksapplication.domain.use_case

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.data.db.BookDao
import com.otaz.nytbooksapplication.data.db.entitiesToBook
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This use case ***
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