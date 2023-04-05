package com.otaz.nytbooksapplication.use_case

import com.google.gson.GsonBuilder
import com.otaz.nytbooksapplication.data.MockWebServerResponses
import com.otaz.nytbooksapplication.db.AppDatabaseFake
import com.otaz.nytbooksapplication.db.BookDaoFake
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
import com.otaz.nytbooksapplication.domain.use_case.SearchBookDbUC
import com.otaz.nytbooksapplication.data.network.NYTApiService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

/**
 * The system in test is use case: [SearchBookDbUC]
 */

class SearchBookDbUCTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val FAKE_APIKEY = "1234"
    private val FAKE_QUERY_EXISTENT = "Smolder"
    private val FAKE_QUERY_NONEXISTENT = "abcdefghijklmnopqrstuvwxyz"
    private val FAKE_CATEGORY = "Hardcover Fiction"
    private val FAKE_DATE = "current"

    // system in test
    private lateinit var searchBookDbUC: SearchBookDbUC

    // Dependencies
    private lateinit var getBookListUC: GetBookListUC
    private lateinit var nytApiService: NYTApiService
    private lateinit var bookDao: BookDaoFake

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/svc/books/v3/")
        nytApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NYTApiService::class.java)

        bookDao = BookDaoFake(appDatabaseFake = appDatabase)

        // to populate database
        getBookListUC = GetBookListUC(
            nytApiService = nytApiService,
            bookDao = bookDao,
        )

        // instantiate the system in test
        searchBookDbUC = SearchBookDbUC(
            bookDao = bookDao,
        )
    }

    @Test
    fun getBooksFromNetwork_emitBooksFromCache(): Unit = runBlocking {

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.responseNormalData)
        )

        // confirm the cache is empty to start
        assert(bookDao.getAllBooks().isEmpty())

        // get books from network and insert into cache
        // in real UC, no network call is made as this data is assumed to be stored in the cache already
        getBookListUC.execute(FAKE_DATE, FAKE_CATEGORY, FAKE_APIKEY).toList()

        val flowBooks = searchBookDbUC.execute(FAKE_QUERY_EXISTENT).toList()

        // confirm the cache is no longer empty
        assert(bookDao.getAllBooks().isNotEmpty())

        // first emission should be `loading`
        assert(flowBooks[0].loading)

        // Second emission should be the list of books
        val books = flowBooks[1].data
        assert((books?.size ?: 0) > 0)

        // confirm they are actually Book objects
        assert(books?.get(index = 0) is Book)

        // loading should be false now
        assert(!flowBooks[1].loading)
    }

    /**
     * This test will attempt to search for a book that does not exist in the cache
     * Result:
     *      1. List of books are retrieved from network and inserted into cache
     *      2. Book is returned as flow from cache
     */
    @Test
    fun attemptSearchNullBookFromCache_searchBooks(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.responseNormalData)
        )

        // confirm the cache is empty to start
        assert(bookDao.getAllBooks().isEmpty())

        // get books from network and insert into cache
        // in real UC, no network call is made as this data is assumed to be stored in the cache already
        getBookListUC.execute(FAKE_DATE, FAKE_CATEGORY, FAKE_APIKEY).toList()

        // confirm the cache is no longer empty
        val cachedBookEntities = bookDao.getAllBooks()
        assert(cachedBookEntities.isNotEmpty())

        // run use case in test using a query that does not exist in the database
        val bookFlow = searchBookDbUC.execute(FAKE_QUERY_NONEXISTENT).toList()

        // first emission should be `loading`
        assert(bookFlow[0].loading)

        // Second emission should be an error
        val error = bookFlow[1].error
        assert(error != null)

        // 'loading' should be false now
        assert(!bookFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}