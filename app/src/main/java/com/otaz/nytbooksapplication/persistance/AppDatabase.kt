package com.otaz.nytbooksapplication.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object{
        val DATABASE_NAME = "book_db"
    }
}