package com.otaz.nytbooksapplication.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SBResultEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object{
        val DATABASE_NAME = "book_db"
    }
}