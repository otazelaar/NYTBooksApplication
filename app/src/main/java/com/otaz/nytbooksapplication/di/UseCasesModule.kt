package com.otaz.nytbooksapplication.di

import com.otaz.nytbooksapplication.network.NYTApiService
import com.otaz.nytbooksapplication.persistance.ArticleDao
import com.otaz.nytbooksapplication.use_cases.GetSavedBookUC
import com.otaz.nytbooksapplication.use_cases.SearchBookUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun searchBookUC(
        nytApiService: NYTApiService,
        articleDao: ArticleDao,
    ): SearchBookUC {
        return SearchBookUC(
            nytApiService = nytApiService,
            articleDao = articleDao,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetSavedBook(
        articleDao: ArticleDao,
    ): GetSavedBookUC {
        return GetSavedBookUC(
            articleDao = articleDao,
        )
    }
}