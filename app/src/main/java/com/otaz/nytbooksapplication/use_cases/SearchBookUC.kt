package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.model.toBookEntity
import com.otaz.nytbooksapplication.domain.model_fake.SBResult
import com.otaz.nytbooksapplication.domain.model_fake.toResultEntity
import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.network.fake.toSBResult
import com.otaz.nytbooksapplication.persistance.BookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.filterList

class SearchBookUC(
    private val nytApiService: NYTApiService,
    private val bookDao: BookDao,
) {
    fun execute(
        apikey: String,
        title: String,
    ): Flow<DataState<List<SBResult>>> = flow {
        try {
            emit(DataState.loading())

            val books = getBookList(apikey, title)

            emit(DataState.success(books))

            // The list of books needs to be cached in the database so that the detail screen can access them
            val booksEntity = books.map { it.toResultEntity() }
            bookDao.insertBooks(booksEntity)

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetCurrentBestSellerListByCategory: Unknown error"))
        }
    }

    private suspend fun getBookList(
        apikey: String,
        title: String
    ): List<SBResult> {
        return nytApiService.searchBookListHistoryByTitle(
            apikey, title
        ).sbResultsDto.filter{
            it.description != null &&
            it.author != null &&
            it.publisher != null
        }.map { it.toSBResult() }
    }
}