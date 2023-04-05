package com.otaz.nytbooksapplication.di

import androidx.room.Room
import com.otaz.nytbooksapplication.data.db.AppDatabase
import com.otaz.nytbooksapplication.data.db.BookDao
import com.otaz.nytbooksapplication.di.UseCaseModule.provideGetBookListUC
import com.otaz.nytbooksapplication.di.UseCaseModule.provideGetSavedBook
import com.otaz.nytbooksapplication.di.UseCaseModule.provideSearchBookDbUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides dependencies [provideDb] & [provideBookDao] for caching in the Room database.
 */

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBookDao(app: AppDatabase): BookDao {
        return app.bookDao()
    }
}