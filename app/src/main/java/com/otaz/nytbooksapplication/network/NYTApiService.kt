package com.otaz.nytbooksapplication.network

import com.otaz.nytbooksapplication.network.fake.SBResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApiService {

    @GET("lists/best-sellers/history.json")
    suspend fun searchBookListHistoryByTitle(
        @Query("api-key") apikey: String,
        @Query("title") title: String,
    ): SBResponse
}