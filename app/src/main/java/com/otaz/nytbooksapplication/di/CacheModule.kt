package com.otaz.nytbooksapplication.di

import androidx.room.Room
import com.otaz.nytbooksapplication.data.db.AppDatabase
import com.otaz.nytbooksapplication.data.db.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * CacheModule provides the necessary dependencies for caching such as:
 *      - AppDatabase
 *      - BookDao
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