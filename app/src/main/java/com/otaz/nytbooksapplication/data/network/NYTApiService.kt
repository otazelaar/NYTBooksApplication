package com.otaz.nytbooksapplication.data.network

import com.otaz.nytbooksapplication.data.network.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Use offset to do pagination for the list to handle if list is larger than 20
 * the current list size for hardcover fiction is of size 15. I am not sure how common it is
 * for the size to be larger than this but we should account for it.
 */
interface NYTApiService {

    // use "current" for current date, o/w specify date
    @GET("lists/{date}/{list}.json")
    suspend fun getBookListFromNetwork(
        @Path("date") date: String,
        @Path("list") category: String,
        @Query("api-key") apikey: String,
    ): Response
}