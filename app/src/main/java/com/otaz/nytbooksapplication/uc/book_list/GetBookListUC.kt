package com.otaz.nytbooksapplication.uc.book_list

import com.otaz.nytbooksapplication.domain.data.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.network.model.BookDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * The following use case...
 */

class GetBookListUC(
    private val nytApiService: NYTApiService,
    private val bookDtoMapper: BookDtoMapper
) {
    fun execute(
        apikey: String,
        query: String,
        page: Int,
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            val movies = getBooksFromNetwork(
                apikey = apikey,
                query = query,
                page = page,
            )

            emit(DataState.success(movies))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetBookListUC: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getBooksFromNetwork(
        apikey: String,
        query: String,
        page: Int,
    ): List<Book> {
        return bookDtoMapper.toDomainList(
            nytApiService.getBookList(
                apikey = apikey,
                query = query,
                page = page,
            ).books
        )
    }
}