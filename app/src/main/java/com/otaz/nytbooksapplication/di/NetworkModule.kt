package com.otaz.nytbooksapplication.di

import com.otaz.nytbooksapplication.data.network.NYTApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * NetworkModule provides the necessary dependencies for the network such as:
 *      - The New York Times API service
 *      - API Key
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNYTApiService(): NYTApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build();
        return Retrofit.Builder().baseUrl("https://api.nytimes.com/svc/books/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYTApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("nyt_apikey")
    fun provideNYTApiKey(): String{
        return "TYqRZecpZgXeXNGSGfHmhwmWNw2jsx1N"
    }
}