package com.otaz.nytbooksapplication.use_cases

import android.annotation.SuppressLint
import com.otaz.nytbooksapplication.domain.DataState
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.model.toBookEntity
import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.network.model.toBook
import com.otaz.nytbooksapplication.persistance.BookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

/**
 *  This use case searches a list of all the NYT best selling books in the database by title.
 *      These results are cached and used in the BookDetailScreen.
 *
 *      Title search can be part of the title or the whole title.
 *
 *
 *      Goals:
 *          - try to make this search actively pop up results on a view
 *          - implement pagination if able on the list results
 *          - save the values for the list_name, bestsellers_date, and ISBN13 so that they can be used
 *          for the next API call
 *          - Maybe try to store the results in the cache of this API call and then get a book by the
 *          ISBN13 from the cache
 *
 */

class SearchBookUC(
    private val nytApiService: NYTApiService,
    private val bookDao: BookDao,
) {
    @SuppressLint("SimpleDateFormat")
    fun execute(
        apikey: String,
        title: String,
    ): Flow<DataState<List<Book>>> = flow {
        try {
            emit(DataState.loading())

            // not sure if data is structured for this response but well try it
            val books = searchHistoryForBookByTitle(apikey, title)
            emit(DataState.success(books))

            // cached data in DB to be displayed in BookDetailScreen
            val booksEntity = books.map { it.toBookEntity() }
            bookDao.insertBooks(booksEntity)

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetCurrentBestSellerListByCategory: Unknown error"))
        }
    }

    private suspend fun searchHistoryForBookByTitle(
        apikey: String,
        title: String
    ): List<Book> {
        return nytApiService.searchBookListHistoryByTitle(
            apikey, title
        ).resultsDto.booksDto.map { it.toBook() }
    }
}

///**
// * The following functions are used to get the current date to be used to search for the current best sellers list
// */
//fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
//    val formatter = SimpleDateFormat(format, locale)
//    return formatter.format(this)
//}
//
//fun getCurrentDateTime(): Date {
//    return Calendar.getInstance().time
//}
//
//val date = getCurrentDateTime()
//val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
