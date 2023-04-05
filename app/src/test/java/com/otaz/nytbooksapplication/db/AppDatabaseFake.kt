package com.otaz.nytbooksapplication.db

import com.otaz.nytbooksapplication.data.db.BookEntity

/**
 * Fake entity from the database for testing
 */
class AppDatabaseFake{
    val books = mutableListOf<BookEntity>()
}