package com.otaz.nytbooksapplication.use_cases

import com.otaz.nytbooksapplication.db.AppDatabaseFake
import com.otaz.nytbooksapplication.db.BookDaoFake
import com.otaz.nytbooksapplication.data.MockWebServerResponses
import com.otaz.nytbooksapplication.network.NYTApiService
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
 * The system in test is use case: [GetBookListByCategoryUCTest]
 *
 *      The plan for testing mirrors that use case and is ordered as follows:
 *      1. Test that loading status is emitted
 *      2. Test if cache is empty to start
 *      3. Test if cache is no longer empty after executing use case
 *          a. if complete, this means the network operation is complete and all the queries are working properly
 *      4. Test that data, List<Movie>, is emitted as a flow from the cache to the UI
 *      5. Test that loading status is changed to false
 */

class GetBookListByCategoryUCTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val FAKE_APIKEY = "1234"
    private val FAKE_QUERY = "Hardcover Fiction"


    // System in test
    private lateinit var getBookListByCategoryUC: GetBookListByCategoryUC

    // Dependencies
    private lateinit var nytApiService: NYTApiService
    private lateinit var bookDao: BookDaoFake


    @BeforeEach
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // maybe try "/" if "." doesn't work. technically mitch says we need to use whatever comes before the "." but the only text before that is the base url.
        baseUrl = mockWebServer.url("lists/{date}/{list}.json")
        nytApiService = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYTApiService::class.java)

        bookDao = BookDaoFake(appDatabase)

        // Instantiate the system in test
        getBookListByCategoryUC = GetBookListByCategoryUC(
            nytApiService = nytApiService,
            bookDao = bookDao,
        )

    }

    @Test
    fun getMoviesFromNetwork_emitMoviesFromCache(): Unit = runBlocking{
        // condition the response that you would normally return from a mock web server
        // mockWebServer is configured to return a successful response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.response)
        )

        // Confirm the cache is empty to start
        assert(movieDao.getAllMovies(1, 10).isEmpty())

        // toList() will simulate flow emission
        val flowItems = getBookListByCategoryUC.execute(
            apikey = FAKE_APIKEY,
            query = FAKE_QUERY,
            page = 1,
            isNetworkAvailable = true,
        ).toList()

        // Confirm the cache is no longer empty
        assert(movieDao.getAllMovies(1, 10).isNotEmpty())

        // First emission should be LOADING
        assert(flowItems[0].loading)

        // Second emission should be the list of movies
        // We are checking if the listed of movies emitted is greater than 0
        val movies = flowItems[1].data
        assert((movies?.size ?: 0) > 0)

        // Confirm they are actually Movie objects
        assert(movies?.get(index = 0) is Movie)

        // Confirm that loading is false
        assert(!flowItems[1].loading)
    }

    @Test
    fun getMoviesFromNetwork_emitHttpError(): Unit = runBlocking {
        // mockWebServer is configured to return the an error response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getBookListByCategoryUC.execute(
            apikey = FAKE_APIKEY,
            query = FAKE_QUERY,
            page = 1,
            isNetworkAvailable = true,
        ).toList()

        // First emission should be LOADING
        assert(flowItems[0].loading)

        // Second emission should be an error
        val error = flowItems[1].error
        assert(error != null)

        // Confirm that loading is false
        assert(!flowItems[1].loading)

    }

    @AfterEach
    fun shutDown(){
        mockWebServer.shutdown()
    }
}