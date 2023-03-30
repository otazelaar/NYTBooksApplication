package com.otaz.nytbooksapplication.network

import com.otaz.nytbooksapplication.network.responses.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApiService {

        @GET("search/movie")
        suspend fun getBookList(
            @Query("api_key") apikey: String,
            @Query("query", encoded = true) query: String,
            @Query("page") page: Int,
        ): BookResponse
}