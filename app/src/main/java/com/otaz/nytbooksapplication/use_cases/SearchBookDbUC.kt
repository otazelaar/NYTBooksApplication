package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.persistance.BookDao
import com.otaz.nytbooksapplication.persistance.entitiesToBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchBookDbUC(
    private val bookDao: BookDao,
) {
    fun execute(
        search: String
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            val listOfBookEntity = bookDao.loadBooks(search)

            val listOfBook = entitiesToBook(listOfBookEntity)

            emit(DataState.success(listOfBook))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "SearchBookDbUC: Error"))
        }
    }
}