package com.otaz.nytbooksapplication.use_case

import com.otaz.nytbooksapplication.db.AppDatabaseFake
import com.otaz.nytbooksapplication.db.BookDaoFake
import com.otaz.nytbooksapplication.data.MockWebServerResponses
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
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
 * The system in test is use case: [GetBookListUCTest]
 * Test [responseNormalData]: Passed
 * Test [responseMalformedData]: failed as expected
 */

class GetBookListUCTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val FAKE_APIKEY = "1234"
    private val FAKE_CATEGORY = "Hardcover Fiction"
    private val FAKE_DATE = "current"

    // System in test
    private lateinit var getBookListUC: GetBookListUC

    // Dependencies
    private lateinit var nytApiService: NYTApiService
    private lateinit var bookDao: BookDaoFake

    @BeforeEach
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        baseUrl = mockWebServer.url("/svc/books/v3/")
        nytApiService = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYTApiService::class.java)

        bookDao = BookDaoFake(appDatabase)

        getBookListUC = GetBookListUC(
            nytApiService = nytApiService,
            bookDao = bookDao,
        )
    }

    @Test
    fun getBooksFromNetwork_emitBooksFromCache(): Unit = runBlocking{
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.responseMalformedData)
        )

        val flowItems = getBookListUC.execute(
            date = FAKE_DATE,
            category = FAKE_CATEGORY,
            apikey = FAKE_APIKEY,
        ).toList()

        assert(bookDao.getAllBooks().isNotEmpty())

        assert(flowItems[0].loading)

        val booksFlow = flowItems[1].data
        assert((booksFlow?.size ?: 0) > 0)

        assert(booksFlow?.get(index = 0) is Book)

        assert(!flowItems[1].loading)
    }

    @Test
    fun getBooksFromNetwork_emitHttpError(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getBookListUC.execute(
            date = FAKE_DATE,
            category = FAKE_CATEGORY,
            apikey = FAKE_APIKEY,
        ).toList()

        assert(flowItems[0].loading)

        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading)
    }

    @AfterEach
    fun shutDown(){
        mockWebServer.shutdown()
    }
}