package com.otaz.nytbooksapplication.data.network

import com.otaz.nytbooksapplication.data.network.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTApiService {

    /**
     * Returns a list of books by category for a specified date
     */
    @GET("lists/{date}/{list}.json")
    suspend fun getBookListFromNetwork(
        @Path("date") date: String,
        @Path("list") category: String,
        @Query("api-key") apikey: String,
    ): Response
}