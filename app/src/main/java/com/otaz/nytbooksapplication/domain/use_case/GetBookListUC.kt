package com.otaz.nytbooksapplication.domain.use_case

import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.model.toBookEntity
import com.otaz.nytbooksapplication.data.network.NYTApiService
import com.otaz.nytbooksapplication.data.network.model.toBook
import com.otaz.nytbooksapplication.data.db.BookDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This use case retrieves List<BookDto> from the network and maps them to List<Book> so they can be
 *      emitted in a flow for updating the current list of books to be viewed in the UI.
 *
 *      List<Book> is then mapped to List<BookEntity> so that the data can be cached in the database.
 *      List<BookEntity> needs to be cached so that the BookDetailViewModel can access them. This is
 *      necessary as the API does not have a GET request for retrieving a single book by its name or ID.
 *
 *      The purpose of the following function is to retrieve network data, display it in the UI and
 *      cache that data in the database. Each time the insertBooks() function is called, new books
 *      will be inserted into the database and books that match the primary key will be updated.
 *      This caching system does not account for deleting old books that are no longer present on
 *      the best seller list and as a result is inherently flawed as it will cause the database to
 *      contain books not currently on the list. Given more time, I would likely use the Room
 *      database @Transaction feature to delete old books as well as verify books date
 *      either update or upsert the date.
 *
 */

class GetBookListUC(
    private val nytApiService: NYTApiService,
    private val bookDao: BookDao,
) {
    fun execute(
        date: String,
        category: String,
        apikey: String
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            // to display shimmer animation
            delay(700)

            // Get books from the network
            val books = getBookListByCategory(date, category, apikey)

            // Emit books to the flow
            emit(DataState.success(books))

            // The list of books needs to be cached in the database so that the detail screen can access them
            val booksEntity = books.map { it.toBookEntity() }
            bookDao.insertBooks(booksEntity)

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetCurrentBestSellerListByCategory: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // the BookEntity can be used to store them in the room database for later access by the BookDetailScreen
    // The following function retrieves List<BookDto> from the network and maps them to List<BookEntity> for cachin
    private suspend fun getBookListByCategory(
        date: String,
        category: String,
        apikey: String,
    ): List<Book> {
        return nytApiService.getBookListFromNetwork(
            date, category, apikey
        ).results.booksDto.map { it.toBook() }
    }
}