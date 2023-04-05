package com.otaz.nytbooksapplication.di

import android.content.Context
import com.otaz.nytbooksapplication.di.UseCaseModule.provideGetBookListUC
import com.otaz.nytbooksapplication.di.UseCaseModule.provideGetSavedBook
import com.otaz.nytbooksapplication.di.UseCaseModule.provideSearchBookDbUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides dependency [provideApplication]
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
}