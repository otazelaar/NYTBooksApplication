//package com.otaz.nytbooksapplication.use_cases
//
//import com.otaz.nytbooksapplication.domain.DataState
//import com.otaz.nytbooksapplication.domain.model.Book
//import com.otaz.nytbooksapplication.domain.model.toBookEntity
//import com.otaz.nytbooksapplication.network.NYTApiService
//import com.otaz.nytbooksapplication.network.model.toBook
//import com.otaz.nytbooksapplication.persistance.BookDao
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
///**
// * The following use case retrieves List<BookDto> from the network and maps them to List<BookEntity>
// *     So they can be cached in the database. List<BookEntity> needs to be cached so that the
// *     BookDetailViewModel can access them. This is necessary as the API does not have a GET request
// *     for getting a book by its name or ID.
// *
// *     The purpose of the following function is to retrieve network data, display the in the UI and
// *     cache that data in the database. When called for a second time, the caching system should either
// *     update or upsert the date. As a result, the database will continue to grow as the list of data changes
// *     overtime. To be extra clear, in the future when the list changes, the network will call the current list
// *     at that time, display that network data in the BookListScreen, and then cache the new data which will
// *     add new Books and update any books that are still present. This method helps me avoid managing deletion
// *     of old books from the database which I am not sure how to set up at this time.
// *
// */
//
//class GetBookListUC(
//    private val nytApiService: NYTApiService,
//    private val bookDao: BookDao,
//) {
//    fun execute(
//        date: String,
//        category: String,
//        apikey: String
//    ): Flow<DataState<List<Book>>> = flow {
//        try {
//            emit(DataState.loading())
//
//            val books = getBookList(date, category, apikey)
//            emit(DataState.success(books))
//
//            // The list of books needs to be cached in the database so that the detail screen can access them
//            val booksEntity = books.map { it.toBookEntity() }
//            bookDao.insertBooks(booksEntity)
//
//        } catch (e: Exception) {
//            emit(DataState.error(e.message ?: "GetCurrentBestSellerListByCategory: Unknown error"))
//        }
//    }
//
//    // This can throw an exception if there is no network connection
//    // the BookEntity can be used to store them in the room database for later access by the BookDetailScreen
//
//    // The following function retrieves List<BookDto> from the network and maps them to List<BookEntity> for cachin
//    private suspend fun getBookList(
//        date: String,
//        category: String,
//        apikey: String,
//    ): List<Book> {
//        return nytApiService.getBookListFromNetwork(
//            date, category, apikey
//        ).resultsDto.booksDto.map { it.toBook() }
//    }
//}