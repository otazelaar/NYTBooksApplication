package com.otaz.nytbooksapplication.di

import com.otaz.nytbooksapplication.data.network.NYTApiService
import com.otaz.nytbooksapplication.data.db.BookDao
import com.otaz.nytbooksapplication.domain.use_case.GetSavedBookUC
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
import com.otaz.nytbooksapplication.domain.use_case.SearchBookDbUC
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
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetBookListUC(
        nytApiService: NYTApiService,
        bookDao: BookDao,
    ): GetBookListUC {
        return GetBookListUC(
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