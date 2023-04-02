package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model_fake.SBResult
import com.otaz.nytbooksapplication.persistance.ArticleDao
import com.otaz.nytbooksapplication.persistance.toSBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedBookUC(
    private val articleDao: ArticleDao,
) {
    fun execute(
        title: String
    ): Flow<DataState<SBResult?>> = flow {
        try {
            emit(DataState.loading())

            val savedBook = articleDao.getArticleById(title)
            val cachedBook = savedBook?.toSBResult()

            emit(DataState.success(cachedBook))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Error"))
        }
    }
}