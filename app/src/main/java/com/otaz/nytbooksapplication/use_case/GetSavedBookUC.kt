package com.otaz.nytbooksapplication.use_case

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.db.BookDao
import com.otaz.nytbooksapplication.db.toBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This use case returns the data from a single book in the cache by using the book_id obtained
 *      from the user clicking on an item/book in the BookList. This data is mapped from the
 *      BookEntity data class to the [Book] data class.
 *
 *      This use case does not account for the cache being empty because and depends on the
 *      GetBookListUC caching the default "Hardcover Fiction" category list on the initial launch of
 *      the application.
 */

class GetSavedBookUC(
    private val bookDao: BookDao,
) {
    fun execute(
        book_id: String
    ): Flow<DataState<Book?>> = flow {
        try {
            emit(DataState.loading())

            val savedBook = bookDao.getBookById(book_id)
            val cachedBook = savedBook?.toBook()

            emit(DataState.success(cachedBook))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Error"))
        }
    }
}