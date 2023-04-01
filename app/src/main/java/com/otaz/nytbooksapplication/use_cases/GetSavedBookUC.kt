package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.persistance.BookDao
import com.otaz.nytbooksapplication.persistance.toBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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