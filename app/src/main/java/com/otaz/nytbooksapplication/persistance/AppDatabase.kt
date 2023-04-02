package com.otaz.nytbooksapplication.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun articleDao(): ArticleDao

    companion object{
        val DATABASE_NAME = "article_db"
    }
}