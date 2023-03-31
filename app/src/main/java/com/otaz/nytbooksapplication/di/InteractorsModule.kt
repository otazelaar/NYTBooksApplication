package com.otaz.nytbooksapplication.di

import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.use_cases.book_list.GetCurrentBestSellerListByCategoryUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetCurrentBestSellerListByCategoryUC(
        nytApiService: NYTApiService,
    ): GetCurrentBestSellerListByCategoryUC {
        return GetCurrentBestSellerListByCategoryUC(
            nytApiService = nytApiService,
        )
    }
}