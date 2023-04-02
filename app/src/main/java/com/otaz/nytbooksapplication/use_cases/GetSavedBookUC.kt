package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model_fake.SBResult
import com.otaz.nytbooksapplication.persistance.BookDao
import com.otaz.nytbooksapplication.persistance.toSBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedBookUC(
    private val bookDao: BookDao,
) {
    fun execute(
        title: String
    ): Flow<DataState<SBResult?>> = flow {
        try {
            emit(DataState.loading())

            val savedBook = bookDao.getBookById(title)
            val cachedBook = savedBook?.toSBResult()

            emit(DataState.success(cachedBook))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Error"))
        }
    }
}