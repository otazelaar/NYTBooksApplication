package com.otaz.nytbooksapplication.use_cases.book_list

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.network.mappers.BookDtoMapper
import com.otaz.nytbooksapplication.network.mappers.ResultsDtoMapper
import com.otaz.nytbooksapplication.network.model.toBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * The following use case...
 */

class GetCurrentBestSellerListByCategoryUC(
    private val nytApiService: NYTApiService,
    private val bookDtoMapper: BookDtoMapper,
) {
    fun execute(
        date: String,
        category: String,
        apikey: String
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            val books = getCurrentBestSellerListByCategory(
                date = date,
                category = category,
                apikey = apikey,
            )

            emit(DataState.success(books))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetCurrentBestSellerListByCategory: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getCurrentBestSellerListByCategory(
        date: String,
        category: String,
        apikey: String,
    ): List<Book> {
        return bookDtoMapper.mapToDomainModel(
            nytApiService.getCurrentBestSellerListByCategory(
                date, category, apikey
            )
        )
    }
}