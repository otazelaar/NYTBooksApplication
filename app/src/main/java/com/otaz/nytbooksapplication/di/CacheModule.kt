package com.otaz.nytbooksapplication.di

import androidx.room.Room
import com.otaz.nytbooksapplication.persistance.AppDatabase
import com.otaz.nytbooksapplication.persistance.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    fun provideBookDao(app: AppDatabase): ArticleDao {
        return app.articleDao()
    }
}