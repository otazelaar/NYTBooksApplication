package com.otaz.nytbooksapplication.network

import com.otaz.nytbooksapplication.network.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 
 */

interface NYTApiService {

    @GET("mostpopular/v2/viewed/1.json")
    suspend fun getListOfArticlesViewedOneDay(
        @Query("api-key") apikey: String,
    ): Response
}