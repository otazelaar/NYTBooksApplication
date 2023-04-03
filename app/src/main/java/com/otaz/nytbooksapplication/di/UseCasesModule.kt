package com.otaz.nytbooksapplication.di

import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.persistance.BookDao
import com.otaz.nytbooksapplication.use_cases.GetSavedBookUC
import com.otaz.nytbooksapplication.use_cases.GetBookListByCategoryUC
import com.otaz.nytbooksapplication.use_cases.SearchBookDbUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * UseCasesModule provides the necessary dependencies for each use case:
 *      - GetBookListUC
 *      - GetSavedBook
 *      - SearchBookDBUC
 */

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideGetBookListByCategoryUC(
        nytApiService: NYTApiService,
        bookDao: BookDao,
    ): GetBookListByCategoryUC {
        return GetBookListByCategoryUC(
            nytApiService = nytApiService,
            bookDao = bookDao,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetSavedBook(
        bookDao: BookDao,
    ): GetSavedBookUC {
        return GetSavedBookUC(
            bookDao = bookDao,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSearchBookDbUC(
        bookDao: BookDao,
    ): SearchBookDbUC {
        return SearchBookDbUC(
            bookDao = bookDao,
        )
    }
}