package com.otaz.nytbooksapplication.use_case

import com.google.gson.GsonBuilder
import com.otaz.nytbooksapplication.data.MockWebServerResponses
import com.otaz.nytbooksapplication.db.AppDatabaseFake
import com.otaz.nytbooksapplication.db.BookDaoFake
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
import com.otaz.nytbooksapplication.domain.use_case.GetSavedBookUC
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
 * This test uses the MockWebServer to populate the list of books in the database despite
 * the actual GetSavedBookUC not using the network. This is because this use case depends on
 * the application populating the default "Hardcover Fiction" list on launch.
 */

class GetSavedBookUCTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val FAKE_APIKEY = "1234"
    private val FAKE_CATEGORY = "Hardcover Fiction"
    private val FAKE_DATE = "current"

    // System in test
    private lateinit var getSavedBookUC: GetSavedBookUC
    private val FAKE_BOOK_ID_EXISTENT = "9781984804495"
    private val FAKE_BOOK_ID_NONEXISTENT = ""

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

        getBookListUC = GetBookListUC(
            nytApiService = nytApiService,
            bookDao = bookDao,
        )

        getSavedBookUC = GetSavedBookUC(
            bookDao = bookDao,
        )
    }

    /**
     * 1. Get list of books from the network and insert into cache
     * 2. Try to retrieve a book by its specific book id
     */
    @Test
    fun getBooksFromCache_getBookById(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.responseNormalData)
        )

        assert(bookDao.getAllBooks().isEmpty())

        // get books from network and insert into cache
        // in real UC, no network call is made as this data is assumed to be stored in the cache already
        getBookListUC.execute(FAKE_DATE, FAKE_CATEGORY, FAKE_APIKEY).toList()

        assert(bookDao.getAllBooks().isNotEmpty())

        val bookAsFlow = getSavedBookUC.execute(FAKE_BOOK_ID_EXISTENT).toList()

        assert(bookAsFlow[0].loading)

        val book = bookAsFlow[1].data
        assert(book?.id == FAKE_BOOK_ID_EXISTENT)

        assert(book is Book)

        assert(!bookAsFlow[1].loading)
    }


    /**
     * This test will attempt to get a book that does not exist in the cache. Use case should fail.
     * Result:
     *      1. List of books are retrieved from network and inserted into cache
     *      2. Failure to get book from cache because BOOK_ID_NULL does not exist
     */

    @Test
    fun attemptGetNullBookFromCache_getBookById(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.responseNormalData)
        )

        assert(bookDao.getAllBooks().isEmpty())

        // get books from network and insert into cache
        // in real UC, no network call is made as this data is assumed to be stored in the cache already
        getBookListUC.execute(FAKE_DATE, FAKE_CATEGORY, FAKE_APIKEY).toList()

        assert(bookDao.getAllBooks().isNotEmpty())

        // run use case with an ID that does not exist in the database
        val bookFlow = getSavedBookUC.execute(FAKE_BOOK_ID_NONEXISTENT).toList()

        assert(bookFlow[0].loading)

        // Second emission should be an error
        val error = bookFlow[1].error
        assert(error != null)

        // confirm their is no book in the cache that matches the ID used
        assert(bookDao.getBookById(FAKE_BOOK_ID_EXISTENT)?.id != FAKE_BOOK_ID_NONEXISTENT)

        assert(!bookFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}