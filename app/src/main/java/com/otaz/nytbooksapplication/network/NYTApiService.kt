package com.otaz.nytbooksapplication.network

import com.otaz.nytbooksapplication.network.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Use offset to do pagination for the list to handle if list is larger than 20
 * the current list size for hardcover fiction is of size 15. I am not sure how common it is
 * for the size to be larger than this but we should account for it.
 *
 * Use the following function similarly to the searching by category in IMDB app
 *      searchBookListByDateAndCategory()
 *          Yes images
 *          Too many categories
 *          date can be "current"
 *
 *      searchBookListHistoryByTitle()
 *          No images.
 *
 *      searchBookListTop5EachCategoryByDate()
 *          Yes images
 *          Current Date needs to be actual date
 *
 *
 *      It seems like searchBookListTop5EachCategoryByDate() displaying a list with the images,
 *      caching them, and then navigating to the next screen from the cache is probably the best call.
 *      This does not allow searching though...
 *
 */
interface NYTApiService {

//    // use "current" for current date, o/w specify date
//    @GET("lists/{date}/{list}.json")
//    suspend fun getBookListFromNetwork(
//        @Path("date") date: String,
//        @Path("list") category: String,
//        @Query("api-key") apikey: String,
//    ): Response

    // maybe make a second api call to search by author as well and control which network call gets
    // made in the use case. also pull user input into use case from the ViewModel to filter for which api call
    // to make.
    @GET("lists/best-sellers/history.json")
    suspend fun searchBookListHistoryByTitle(
        @Query("api-key") apikey: String,
        @Query("title") title: String,
    ): Response
}